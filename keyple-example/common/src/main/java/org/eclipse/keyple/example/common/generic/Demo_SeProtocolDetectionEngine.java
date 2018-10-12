/*
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License version 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 */

package org.eclipse.keyple.example.common.generic;

import java.util.*;
import org.eclipse.keyple.calypso.transaction.PoSelector;
import org.eclipse.keyple.example.common.calypso.HoplinkInfo;
import org.eclipse.keyple.seproxy.*;
import org.eclipse.keyple.seproxy.event.ObservableReader;
import org.eclipse.keyple.seproxy.event.ReaderEvent;
import org.eclipse.keyple.seproxy.protocol.ContactlessProtocols;
import org.eclipse.keyple.transaction.SeSelection;
import org.eclipse.keyple.transaction.SeSelector;
import org.eclipse.keyple.util.ByteArrayUtils;

/**
 * This code demonstrates the multi-protocols capability of the Keyple SeProxy:
 * <ul>
 * <li>instantiates a PC/SC plugin for a reader which name matches the regular expression provided
 * by poReaderName.</li>
 * <li>uses the observable mechanism to handle SE insertion/detection</li>
 * <li>expects SE with various protocols (technologies)</li>
 * <li>shows the identified protocol when a SE is detected</li>
 * <li>executes a simple Hoplink reading when a Hoplink SE is identified</li>
 * </ul>
 * The program spends most of its time waiting for a Enter key before exit. The actual SE processing
 * is mainly event driven through the observability.
 */

/**
 * This class handles the reader events generated by the SeProxyService
 */
public class Demo_SeProtocolDetectionEngine implements ObservableReader.ReaderObserver {
    private ProxyReader poReader;

    public Demo_SeProtocolDetectionEngine() {
        super();
    }

    /* Assign reader to the transaction engine */
    public void setReader(ProxyReader poReader) {
        this.poReader = poReader;
    }

    public void initialize() {

    }

    /**
     * This method is called whenever a reader event occurs.
     *
     * @param event the current event
     */
    @Override
    public void update(ReaderEvent event) {
        switch (event.getEventType()) {
            case SE_INSERTED:
                System.out.println("SE INSERTED");
                System.out.println("\nStart processing of a PO");
                operatePoTransaction();
                break;
            case SE_REMOVAL:
                System.out.println("SE REMOVED");
                System.out.println("\nWait for PO");
                break;
            default:
                System.out.println("IO Error");
        }
    }

    /**
     * This method is called when a SE is inserted (or presented to the reader's antenna). It
     * executes a SeRequestSet and processes the SeResponseSet showing the APDUs exchanges
     */
    public void operatePoTransaction() {

        try {
            SeSelection seSelection = new SeSelection(poReader);

            ApduRequest pcscContactlessReaderGetData =
                    new ApduRequest(ByteArrayUtils.fromHex("FFCA000000"), false);
            List<ApduRequest> pcscContactlessReaderGetDataList = new ArrayList<ApduRequest>();
            pcscContactlessReaderGetDataList.add(pcscContactlessReaderGetData);

            // process SDK defined protocols
            for (ContactlessProtocols protocol : ContactlessProtocols.values()) {
                switch (protocol) {
                    case PROTOCOL_ISO14443_4:
                        /* Add a Calypso selector */
                        PoSelector poSelector =
                                new PoSelector(ByteArrayUtils.fromHex(HoplinkInfo.AID), false,
                                        ContactlessProtocols.PROTOCOL_ISO14443_4,
                                        PoSelector.RevisionTarget.TARGET_REV3);

                        poSelector.preparePoCustomCmd("Standard Get Data",
                                new ApduRequest(ByteArrayUtils.fromHex("FFCA000000"), false));

                        poSelector.prepareReadRecordsCmd(HoplinkInfo.SFI_T2Environment,
                                HoplinkInfo.RECORD_NUMBER_1, true, (byte) 0x00,
                                HoplinkInfo.EXTRAINFO_ReadRecord_T2EnvironmentRec1);

                        seSelection.addSelector(poSelector);

                        break;
                    case PROTOCOL_ISO14443_3A:
                    case PROTOCOL_ISO14443_3B:
                        // not handled in this demo code
                        break;
                    case PROTOCOL_MIFARE_DESFIRE:
                    case PROTOCOL_B_PRIME:
                        // intentionally ignored for demo purpose
                        break;
                    default:
                        /* Add a generic selector */
                        seSelection.addSelector(new SeSelector(".*", false,
                                ContactlessProtocols.PROTOCOL_ISO14443_4));
                        break;
                }
            }

            List<SeResponse> seResponses = seSelection.processSelection().getResponses();

            int requestIndex = 0;
            for (SeResponse seResponse : seResponses) {

                if (seResponse != null) {
                    System.out.println(
                            "Protocol matched for request number " + String.valueOf(requestIndex));
                    List<ApduResponse> poApduResponseList = seResponse.getApduResponses();
                    for (int i = 0; i < poApduResponseList.size(); i++) {
                        System.out.println("RESP: "
                                + ByteArrayUtils.toHex(poApduResponseList.get(i).getBytes()));
                    }
                }
                requestIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

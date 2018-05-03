/*
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License version 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 */

package org.keyple.calypso.commands.csm.builder;

import java.nio.ByteBuffer;
import org.keyple.calypso.commands.CalypsoCommands;
import org.keyple.calypso.commands.csm.AbstractCsmCommandBuilder;
import org.keyple.calypso.commands.csm.CsmRevision;
import org.keyple.calypso.commands.utils.RequestUtils;
import org.keyple.commands.InconsistentCommandException;
import org.keyple.seproxy.ApduRequest;

/**
 * Builder for the Digest Authenticate APDU command.
 */
public class DigestAuthenticateCmdBuild extends AbstractCsmCommandBuilder {

    /** The command. */
    private static CalypsoCommands command = CalypsoCommands.CSM_DIGEST_AUTHENTICATE;

    /**
     * Instantiates a new DigestAuthenticateCmdBuild .
     *
     * @param revision of the CSM(SAM)
     * @param signature the signature
     * @throws InconsistentCommandException the inconsistent command exception
     */
    public DigestAuthenticateCmdBuild(CsmRevision revision, ByteBuffer signature)
            throws InconsistentCommandException {
        super(command, null);
        if (revision != null) {
            this.defaultRevision = revision;
        }
        if (signature == null) {
            throw new InconsistentCommandException("Signature can't be null");
        }
        if (signature.limit() != 4 && signature.limit() != 8 && signature.limit() != 16) {
            throw new InconsistentCommandException(
                    "Signature is not the right length : length is " + signature.limit());
        }
        byte cla = CsmRevision.S1D.equals(this.defaultRevision) ? (byte) 0x94 : (byte) 0x80;
        byte p1 = 0x00;
        byte p2 = (byte) 0x00;

        // CalypsoRequest calypsoRequest = new CalypsoRequest(cla, command, p1, p2, signature);

        request = RequestUtils.constructAPDURequest(cla, command, p1, p2, signature);
    }

    /**
     * Instantiates a new digest authenticate cmd build.
     *
     * @param request the request
     * @throws InconsistentCommandException the inconsistent command exception
     */
    public DigestAuthenticateCmdBuild(ApduRequest request) throws InconsistentCommandException {
        super(command, request);
        RequestUtils.controlRequestConsistency(command, request);
    }

}

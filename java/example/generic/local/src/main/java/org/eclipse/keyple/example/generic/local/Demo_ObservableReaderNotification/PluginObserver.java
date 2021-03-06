/* **************************************************************************************
 * Copyright (c) 2020 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.eclipse.keyple.example.generic.local.Demo_ObservableReaderNotification;

import org.eclipse.keyple.core.service.Reader;
import org.eclipse.keyple.core.service.SmartCardService;
import org.eclipse.keyple.core.service.event.ObservablePlugin;
import org.eclipse.keyple.core.service.event.ObservableReader;
import org.eclipse.keyple.core.service.event.PluginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PluginObserver implements ObservablePlugin.PluginObserver {

  private static final Logger logger = LoggerFactory.getLogger(PluginObserver.class);

  @Override
  public void update(PluginEvent event) {
    for (String readerName : event.getReaderNames()) {
      Reader reader = null;
      logger.info(
          "PluginEvent: PLUGINNAME = {}, READERNAME = {}, EVENTTYPE = {}",
          event.getPluginName(),
          readerName,
          event.getEventType());

      switch (event.getEventType()) {
        case READER_CONNECTED:
          /* We retrieve the reader object from its name. */
          reader =
              SmartCardService.getInstance().getPlugin(event.getPluginName()).getReader(readerName);
          logger.info("New reader! READERNAME = {}", reader.getName());

          /*
           * We are informed here of a disconnection of a reader.
           *
           * We add an observer to this reader if this is possible.
           */
          if (reader instanceof ObservableReader) {

            logger.info("Add observer READERNAME = {}", reader.getName());
            ((ObservableReader) reader).addObserver(new CardReaderObserver());
            ((ObservableReader) reader).startCardDetection(ObservableReader.PollingMode.REPEATING);
          }
          break;
        case READER_DISCONNECTED:
          /*
           * We are informed here of a disconnection of a reader.
           *
           * The reader object still exists but will be removed from the reader list
           * right after. Thus, we can properly remove the observer attached to this
           * reader before the list update.
           */
          logger.info("Reader removed. READERNAME = {}", readerName);
          if (reader instanceof ObservableReader) {
            logger.info("Clear observers of READERNAME = {}", readerName);
            ((ObservableReader) reader).clearObservers();
          }
          break;
        default:
          logger.info("Unexpected reader event. EVENT = {}", event.getEventType().name());
          break;
      }
    }
  }
}

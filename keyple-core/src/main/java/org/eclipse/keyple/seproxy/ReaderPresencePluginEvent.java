/*
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License version 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 */

package org.eclipse.keyple.seproxy;

public class ReaderPresencePluginEvent extends AbstractPluginEvent {
    private final boolean added;
    private final ProxyReader reader;

    public ReaderPresencePluginEvent(boolean added, ProxyReader reader) {
        this.added = added;
        this.reader = reader;
    }

    /**
     * Define if the reader was added or removed
     * 
     * @return true for added
     */
    public boolean isAdded() {
        return added;
    }

    /**
     * Reader that was added or removed
     * 
     * @return Reader
     */
    public ProxyReader getReader() {
        return reader;
    }
}
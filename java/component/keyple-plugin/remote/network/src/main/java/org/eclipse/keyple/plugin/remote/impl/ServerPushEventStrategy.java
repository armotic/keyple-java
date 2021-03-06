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
package org.eclipse.keyple.plugin.remote.impl;

/**
 * (package-private)<br>
 * Server Push Event Strategy
 *
 * <p>This internal class indicates the strategy to adopt in a client-server communication to allow
 * the client to receive events from the server.
 *
 * @since 1.0
 */
final class ServerPushEventStrategy {

  private final Type type;
  private int duration;

  /**
   * (package-private)<br>
   * Creates a new instance with a initial duration set to 0.
   *
   * @param type The strategy type to set.
   * @since 1.0
   */
  ServerPushEventStrategy(Type type) {
    this.type = type;
    this.duration = 0;
  }

  /**
   * (package-private)<br>
   * The strategy type enum.
   *
   * @since 1.0
   */
  enum Type {
    POLLING,
    LONG_POLLING
  }

  /**
   * (package-private)<br>
   * Gets the strategy type.
   *
   * @return a not null value.
   * @since 1.0
   */
  Type getType() {
    return type;
  }

  /**
   * (package-private)<br>
   * Sets the duration associated to the strategy.<br>
   * Must be invoked by the factory during the initialization process.
   *
   * @param durationInSeconds The duration in seconds (must be {@code >= 0})
   * @return the current instance
   * @since 1.0
   */
  ServerPushEventStrategy setDuration(int durationInSeconds) {
    this.duration = durationInSeconds;
    return this;
  }

  /**
   * (package-private)<br>
   * Gets the duration (in seconds).
   *
   * @return the duration (in seconds).
   * @since 1.0
   */
  int getDuration() {
    return duration;
  }
}

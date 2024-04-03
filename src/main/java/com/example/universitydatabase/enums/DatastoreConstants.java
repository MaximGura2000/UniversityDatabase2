package com.example.universitydatabase.enums;

/**
 * Some constants for MongoDB
 */
public final class DatastoreConstants {

  /**
   * Default maximal number of objects, that can be stored under one AWID. -1 = undefined.
   */
  public static final int DEFAULT_MAXIMUM_NOI = 100000;
  /**
   * Default maximal size of one object, that can be stored in bytes.
   */
  public static final int DEFAULT_MAXIMUM_SOI = 16000000;
  /**
   * Default maximal number of application workspaces in one uuSubApp instance.
   */
  public static final int DEFAULT_MAXIMUM_NOA = 10;
  /**
   * Default maximal size of binary data. -1 = undefined.
   */
  public static final int DEFAULT_MAXIMUM_SOB = -1;
  /**
   * Default time to live of lock object in seconds.
   */
  public static final int DEFAULT_LOCK_DURATION = 60;
}

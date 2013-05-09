/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Utility class for storing data to RecordStore and retriving data from
 * RecordStore.
 */
public class RMSUtils {

    /**
     * Save data to RMS.
     * @param recordStoreName
     * @param data 
     */
    public static void save(String recordStoreName, byte[] data) {
        try {
            RecordStore store = RecordStore.openRecordStore(recordStoreName,
                true);
            if (store.getNumRecords() == 0) {
                store.addRecord(null, 0, 0);
            }
            store.setRecord(getRecordId(store), data, 0, data.length);
            store.closeRecordStore();
        }
        catch (Exception e) {
            try {
                RecordStore.deleteRecordStore(recordStoreName);
            }
            catch (RecordStoreException rse) {
            }
        }
    }

    private static int getRecordId(RecordStore store)
        throws RecordStoreException {
        RecordEnumeration e = store.enumerateRecords(null, null, false);
        try {
            return e.nextRecordId();
        }
        finally {
            e.destroy();
        }
    }

    /**
     * Load data from RMS.
     * @param recordStoreName
     * @return stored data
     */
    public static byte[] load(String recordStoreName) {
        byte[] data = null;
        try {
            RecordStore store = RecordStore.openRecordStore(recordStoreName,
                true);
            if (store.getNumRecords() > 0) {
                data = store.getRecord(getRecordId(store));
            }
        }
        catch (RecordStoreException e) {
        }
        return data;
    }
}

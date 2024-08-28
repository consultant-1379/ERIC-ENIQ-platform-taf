package com.ericsson.eniq.taf.installation.dataproviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.annotations.DataSource;
import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.eniq.taf.db.EniqDBOperator;

public class CustomDataSource {

    @Inject
    private EniqDBOperator operator;

    // Result should implement java.lang.Iterable<Map<String,Object>>
    @DataSource
    public List<Map<String, Object>> dataSource() {
        List<Map<String, Object>> l = new ArrayList<>();
        /*
         * Map<String, Object> data = new HashMap<String, Object>(); data.put("testCaseId", "54"); Map<String, String> data1 = new HashMap<String,
         * String>(); data1.put("col2", "3.15"); data1.put("col1", "3.14"); data.put("data", data1); Map<String, String> data2 = new HashMap<String,
         * String>(); data2.put("col2", "3.15"); data2.put("col1", "3.14"); data.put("data",e data2); l.add(data);; return l;
         */
        System.out.println("operator::" + operator);
        if (operator == null) {
            operator = new EniqDBOperator();
        }
        operator.setupDWHREP();
        final TestDataSource<DataRecord> resultSet = operator.executeQuery("pm/pm_get_Configuration.sql");

        final Iterator<DataRecord> itr = resultSet.iterator();

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("testCaseId", "54");
        List<Map<String, String>> dataList = new ArrayList<>();
        while (itr.hasNext()) {
            DataRecord dataRecord = itr.next();
            Map<String, String> data1 = new HashMap<String, String>();
            data1.put("PARAMNAME", (String) dataRecord.getFieldValue("PARAMNAME"));
            data1.put("PARAMVALUE", (String) dataRecord.getFieldValue("PARAMVALUE"));
            dataList.add(data1);
        }
        data.put("data", dataList);
        l.add(data);
        operator.teardown();
        return l;
    }

}
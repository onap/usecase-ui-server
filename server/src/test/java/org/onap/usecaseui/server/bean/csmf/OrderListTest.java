/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.bean.csmf;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderListTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetOrderList() throws Exception {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_creation_time("34562123");
        orderInfo.setOrder_id("sde2345rr");
        orderInfo.setOrder_status("creating");
        orderInfo.setService_snssai("sdffg34-344");
        orderInfo.setOrder_name("dfer-fgree");
        orderInfo.setOrder_index("1");
        orderInfo.setLast_operation_type("activate");
        orderInfo.setLast_operation_progress("79");

        OrderList orderList = new OrderList();
        orderList.setRecord_number(3);

        List<OrderInfo> orderInfoList = new ArrayList<>();
        orderInfoList.add(orderInfo);
        orderList.setSlicing_order_list(orderInfoList);

        orderList.getRecord_number();
        orderList.getSlicing_order_list();

        OrderList orderList1 = new OrderList(3, orderInfoList);
    }
}

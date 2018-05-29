/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.util;

import org.junit.Test;
import org.onap.usecaseui.server.bean.AlarmsHeader;

public class PageTest {
	
	@Test
	public void testPageGet(){
		Page<AlarmsHeader> resultPage = new Page<AlarmsHeader>();
		resultPage.setTotalRecords(20);
		resultPage.setPageSize(5);
		resultPage.setPageNo(5);
		
		resultPage.getList();
		resultPage.getTotalRecords();
		resultPage.getPageSize();
		resultPage.getPageNo();
		resultPage.getTotalPages();
		int topPage=resultPage.getTopageNo();
		resultPage.getPreviousPageNo();
		resultPage.getBottomPageNo();
		resultPage.getNextPageNo();
	}
}

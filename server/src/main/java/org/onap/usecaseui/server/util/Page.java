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

import java.util.List;

public class Page<E> {

	public List<E> list;

	public int totalRecords;

	public int pageSize;

	public int pageNo;
	
	
	
	
	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public int getTotalPages() {
		return (totalRecords+pageSize-1)/pageSize;
	}	
	

	public int countOffset(int currentPage,int pageSize) {
		int offset =pageSize*(currentPage-1);
		return offset;
	}

	public int getTopageNo() {
		return 1;
	}

	public int getPreviousPageNo() {
		if(pageNo<=1) {
			return 1;
		}
		return pageNo-1;
	}

	public int getNextPageNo() {
		if(pageNo>=getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo+1;
	}

	public int getBottomPageNo() {
		return getTotalPages();
	}
	
}

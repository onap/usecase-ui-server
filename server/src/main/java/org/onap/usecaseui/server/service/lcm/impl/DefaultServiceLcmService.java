/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.service.lcm.impl;

import static org.onap.usecaseui.server.util.RestfulServices.extractBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;

import com.google.common.base.Throwables;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOClient;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.SaveOrUpdateOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.stereotype.Service;

import okhttp3.RequestBody;
import retrofit2.Response;

@Slf4j
@Transactional
@Service("ServiceLcmService")
@RequiredArgsConstructor
public class DefaultServiceLcmService implements ServiceLcmService {

    private final EntityManagerFactory entityManagerFactory;
    private final SOClient soClient;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();}

    @Override
    public ServiceOperation instantiateService(HttpServletRequest request) {
        try {
            log.info("so instantiate is starting");
            RequestBody requestBody = extractBody(request);
            Response<ServiceOperation> response = soClient.instantiateService(requestBody).execute();
            log.info("so instantiate has finished");
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error(String.format("Can not instantiate service[code=%s, message=%s]", response.code(),
                        response.message()));
                throw new SOException("SO instantiate service failed!");
            }
        } catch (Exception e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public OperationProgressInformation queryOperationProgress(String serviceId, String operationId) {
        try {
            Response<OperationProgressInformation> response =
                    soClient.queryOperationProgress(serviceId, operationId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error(String.format("Can not query operation process[code=%s, message=%s]", response.code(),
                        response.message()));
                throw new SOException("SO query operation process failed!");
            }
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public DeleteOperationRsp terminateService(String serviceId, HttpServletRequest request) {
        try {
            log.info("so terminate is starting");
            RequestBody requestBody = extractBody(request);
            Response<DeleteOperationRsp> response = soClient.terminateService(serviceId, requestBody).execute();
            log.info("so terminate has finished");
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error(String.format("Can not terminate service[code=%s, message=%s]", response.code(),
                        response.message()));
                throw new SOException("SO terminate service failed!");
            }
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public SaveOrUpdateOperationRsp scaleService(String serviceId, HttpServletRequest request) {
        try {
            log.info("so scale is finished");
            RequestBody requestBody = extractBody(request);
            Response<SaveOrUpdateOperationRsp> response = soClient.scaleService(serviceId, requestBody).execute();
            log.info("so scale has finished");
            if (response.isSuccessful()) {
                log.info("scaleService response content is :" + response.body().toString());
                return response.body();
            } else {
                log.error(String.format("Can not scaleService service[code=%s, message=%s]", response.code(),
                        response.message()));
                throw new SOException("SO terminate service failed!");
            }
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public SaveOrUpdateOperationRsp updateService(String serviceId, HttpServletRequest request) {
        try {
            log.info("so update is starting");
            RequestBody requestBody = extractBody(request);
            Response<SaveOrUpdateOperationRsp> response = soClient.updateService(serviceId, requestBody).execute();
            log.info("so update has finished");
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error(String.format("Can not updateService service[code=%s, message=%s]", response.code(),
                        response.message()));
                throw new SOException("SO terminate service failed!");
            }
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public void saveOrUpdateServiceBean(ServiceBean serviceBean) {
        Session session = getSession();
        try  {
            if (null == serviceBean) {
                log.error("DefaultServiceLcmService saveOrUpdateServiceBean serviceBean is null!");
            }
            session.saveOrUpdate(serviceBean);
            session.flush();
        } catch (Exception e) {
            log.error(
                    "exception occurred while performing DefaultServiceLcmService saveOrUpdateServiceBean. Details:"
                            + e.getMessage());
        }
    }

    @Override
    public void updateServiceInstanceStatusById(String status, String serviceInstanceId) {
        Session session = getSession();
        try  {
            String string = "update ServiceBean set status=:status where 1=1 and serviceInstanceId=:serviceInstanceId";
            Query q = session.createQuery(string);
            q.setParameter("status", status);
            q.setParameter("serviceInstanceId", serviceInstanceId);
            q.executeUpdate();
            session.flush();
        } catch (Exception e) {
            log.error(
                    "exception occurred while performing DefaultServiceLcmService updateServiceInstanceStatusById.Detail."
                            + e.getMessage());
        }
    }

    @Override
    public ServiceBean getServiceBeanByServiceInStanceId(String serviceInstanceId) {
        ServiceBean serviceBean = null;
        Session session = getSession();
        try  {

            String string = "from ServiceBean  where 1=1 and serviceInstanceId=:serviceInstanceId";
            Query q = session.createQuery(string);
            q.setParameter("serviceInstanceId", serviceInstanceId);
            List<ServiceBean> list = q.list();
            session.flush();
            if (list.size() > 0) {
                serviceBean = list.get(0);
            }
        } catch (Exception e) {
            log.error(
                    "exception occurred while performing DefaultServiceLcmService getServiceBeanByServiceInStanceId.Detail."
                            + e.getMessage());
            serviceBean = new ServiceBean();;
        }
        return serviceBean;

    }

    @Override
    public List<String> getServiceInstanceIdByParentId(String parentServiceInstanceId) {
        List<String> list = new ArrayList<>();
        Session session = getSession();
        try  {

            String string = "from ServiceBean  where 1=1 and parentServiceInstanceId=:parentServiceInstanceId";
            Query q = session.createQuery(string);
            q.setParameter("parentServiceInstanceId", parentServiceInstanceId);
            list = q.list();
            session.flush();
        } catch (Exception e) {
            list = new ArrayList<>();
            log.error(
                    "exception occurred while performing DefaultServiceLcmService updateServiceInstanceStatusByIdDetail."
                            + e.getMessage());
        }
        return list;

    }

    @Override
    public void saveOrUpdateServiceInstanceOperation(ServiceInstanceOperations serviceOperation) {
        Session session = getSession();
        try  {
            if (null == serviceOperation) {
                log.error("DefaultServiceLcmService saveOrUpdateServiceBean serviceOperation is null!");
            }
            session.saveOrUpdate(serviceOperation);
            session.flush();
        } catch (Exception e) {
            log.error(
                    "exception occurred while performing DefaultServiceLcmService saveOrUpdateServiceInstanceOperation. Details:"
                            + Throwables.getStackTraceAsString(e));
            log.error(
                    "exception occurred while performing DefaultServiceLcmService saveOrUpdateServiceInstanceOperation. Details:"
                            + e.getMessage());
        }
    }

    @Override
    public void updateServiceInstanceOperation(String serviceInstanceId, String operationType, String progress,
            String operationResult) {
        List<ServiceInstanceOperations> list = new ArrayList<>();
        Session session = getSession();
        try {
            String hql =
                    "select a.* from service_instance_operations a where service_instance_id =:serviceId and operation_type =:operationType and start_time = (select max(start_time) from service_instance_operations where service_instance_id=:serviceInstanceId )";
            Query q = session.createNativeQuery(hql).addEntity(ServiceInstanceOperations.class);
            q.setParameter("serviceId", serviceInstanceId);
            q.setParameter("serviceInstanceId", serviceInstanceId);
            q.setParameter("operationType", operationType);
            list = q.list();
            ServiceInstanceOperations serviceOperation = list.get(0);
            serviceOperation.setOperationResult(operationResult);
            serviceOperation.setOperationProgress(progress);
            if ("100".equals(progress)) {
                serviceOperation.setEndTime(DateUtils.dateToString(DateUtils.now()));
            }
            session.saveOrUpdate(serviceOperation);
            session.flush();

        } catch (Exception e) {
            log.error(
                    "exception occurred while performing DefaultServiceLcmService updateServiceInstanceOperation.Detail."
                            + e.getMessage());
        }
    }

    @Override
    public ServiceInstanceOperations getServiceInstanceOperationById(String serviceId) {
        ServiceInstanceOperations serviceOperation = null;
        List<ServiceInstanceOperations> list = new ArrayList<>();
        Session session = getSession();
        try  {
            String hql =
                    "select a.* from service_instance_operations a where service_instance_id =:serviceId and start_time = (select max(start_time) from service_instance_operations where service_instance_id=:serviceInstanceId)";
            Query q = session.createNativeQuery(hql).addEntity(ServiceInstanceOperations.class);
            q.setParameter("serviceId", serviceId);
            q.setParameter("serviceInstanceId", serviceId);
            q.list();
            list = q.list();
            if (!list.isEmpty()) {
                serviceOperation = list.get(0);
            } else {
                serviceOperation = new ServiceInstanceOperations();
            }
            session.flush();

        } catch (Exception e) {
            log.error("exception occurred while performing DefaultServiceLcmService getServiceInstanceOperationById."
                    + e.getMessage());
        }
        return serviceOperation;


    }


        @Override
	public List<ServiceBean> getAllServiceBean() {
        List<ServiceBean> list = new ArrayList<ServiceBean>();
        Session session = getSession();
        try  {

            String string = "from ServiceBean";
            Query q = session.createQuery(string);
            list = q.list();
            session.flush();
        } catch (Exception e) {
            list = new ArrayList<>();
            log.error(
                    "exception occurred while performing DefaultServiceLcmService updateServiceInstanceStatusByIdDetail."
                            + e.getMessage());
        }
        return list;

    }



}

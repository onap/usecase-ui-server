package org.onap.usecaseui.server.service.intent.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service("IntentService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class IntentServiceImpl implements IntentService {
    private static final Logger logger = LoggerFactory.getLogger(IntentServiceImpl.class);

    private final static String UPLOADPATH = "c:/var/uploadtest/";
    //private final static String UPLOADPATH = "/home/uui/upload/";

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public String addModel(IntentModel model) {
        try(Session session = getSession()){
            if (null == model){
                logger.error("IntentServiceImpl addModel model is null!");
                return "0";
            }
            Transaction tx = session.beginTransaction();
            session.save(model);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }


    }

    public List<IntentModel> listModels(){
        try(Session session = getSession()){
            StringBuffer hql =new StringBuffer("from IntentModel a where 1=1 ");
            Query query = session.createQuery(hql.toString());
            //query.setString("sortType",sortType);
            List<IntentModel> list= query.list();
            return list;
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return Collections.emptyList();
        }
    }

    public IntentModel getModel(String modelId){
        //Transaction tx = null;
        IntentModel result = null;

        try(Session session = getSession()) {
            //tx = session.beginTransaction();

            result = (IntentModel)session.createQuery("from IntentModel where id = :modelId")
                    .setParameter("modelId", Integer.parseInt(modelId)).uniqueResult();
            logger.info("get model OK, id=" + modelId);

        } catch (Exception e) {
//            if(tx!=null){
//                tx.rollback();
//            }
            logger.error("getodel occur exception:"+e);

        }

        return result;
    }

    public String deleteModel(String modelId){
        Transaction tx = null;
        String result="0";
        if(modelId==null || modelId.trim().equals(""))
            return  result;

        try(Session session = getSession()) {
            tx = session.beginTransaction();

            IntentModel model = new IntentModel();
            model.setId(Integer.parseInt(modelId));
            session.delete(model);
            tx.commit();
            logger.info("delete model OK, id=" + modelId);

            result="1";
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("deleteModel occur exception:"+e);

        }
        return result;
    }

    public IntentModel activeModel(String modelId){
        Transaction tx = null;
        IntentModel result=null;
        if(modelId==null || modelId.trim().equals(""))
            return result;

        try(Session session = getSession()) {
            tx = session.beginTransaction();
            List<IntentModel> list = session.createQuery("from IntentModel where active=1").list();
            if(list!=null && list.size()>0){
                for (IntentModel m : list) {
                    m.setActive(0);
                    session.save(m);
                }
            }

            IntentModel model = (IntentModel)session.createQuery("from IntentModel where id = :modelId")
                    .setParameter("modelId", Integer.parseInt(modelId)).uniqueResult();
            model.setActive(1);
            session.save(model);
            tx.commit();
            logger.info("active model OK, id=" + modelId);

            result = model;
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("deleteModel occur exception:"+e);

        }
        return result;
    }

    @Override
    public String activeModelFile(IntentModel model) {
        if (model == null) {
            return null;
        }
        String filePath = model.getFilePath();
        if (filePath == null) {
            return null;
        }
        else if (filePath.endsWith(".zip")){
            try {
                File file = new File(filePath);
                String parentPath = file.getParent();
                String unzipPath = filePath.substring(0, filePath.length() - 4);
                File unZipFile = new File(unzipPath);
                if (!unZipFile.exists()) {
                    ZipUtil.unzip(file,parentPath);
                }
                return unzipPath;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

}

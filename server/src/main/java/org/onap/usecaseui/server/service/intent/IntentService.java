package org.onap.usecaseui.server.service.intent;

import org.onap.usecaseui.server.bean.intent.IntentModel;

import java.util.List;

public interface IntentService {
    public String addModel(IntentModel model);
    List<IntentModel> listModels();
    public String deleteModel(String modelId);
    public IntentModel getModel(String modelId);
    public IntentModel activeModel(String modelId);
    String activeModelFile(IntentModel model);
}

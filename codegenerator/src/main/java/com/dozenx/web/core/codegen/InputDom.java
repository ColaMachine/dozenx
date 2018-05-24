package com.dozenx.web.core.codegen;

/**
 * Created by colamachine on 16-6-27.
 */
public class InputDom implements Component {
    @Override
    public String render(ZColum zcol) {
        String type =zcol.getType().toLowerCase();
        String commonStr= "id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\"  class=\"form-control input-sm\" ";

        return "<>"+"";

    }
}

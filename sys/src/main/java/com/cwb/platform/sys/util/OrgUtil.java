package com.cwb.platform.sys.util;

import com.cwb.platform.util.exception.RuntimeCheck;

public class OrgUtil {

    public static int getLevel(String orgCode){
        RuntimeCheck.ifBlank(orgCode,"机构代码不能为空");
        return orgCode.length()/3;
    }

    public static void main(String[] args) {
    }

}

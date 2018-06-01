
package com.cwb.platform.sys.base;

import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.Serializable;

/**
 * 单表通用CURD Control基础类，适用于单表有CURD业务操作的Controller继承，如果自己的业务不需要更新操作时（包含：新增、修改、删除）可以参考类方法，不继承该基础类
 * 该基础类是一个Rest方式接口，请注意里面提供方法的请求类型和参数内容，每个方法是可以重写的
 * 注意：
 * 1、该基础类提供的方法只返回json数据，不做页面ModelView跳转
 * 2、基础类要在类方法添加注解@RequestMapping，访问入口，防止访问路径冲突
 * @param <T>
 * @param <PK>
 */
public abstract class BaseController<T, PK extends Serializable> extends QueryController<T,PK>{

	/**
     * 数据新增方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
	public ApiResponse<String> save(T entity){
		return getBaseService().validAndSave(entity);
	}

    /**
     * 数据修改方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param entity
     * @return
     */
    @RequestMapping(value="/update", method={RequestMethod.POST})
	public ApiResponse<String> update(T entity){
		return getBaseService().validAndUpdate(entity);
	}

    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
	public ApiResponse<String> remove(@PathVariable("pkid")PK id){
		getBaseService().remove(id);
		return ApiResponse.success();
	}

    /**
	 * 批量删除操作
	 * @param ids
	 * @return
	 */
    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
	public ApiResponse<String> remove(PK[] ids){
		return getBaseService().removeIds(ids);
	}
}

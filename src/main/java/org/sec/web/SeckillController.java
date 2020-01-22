package org.sec.web;

import java.util.Date;
import java.util.List;

import javax.xml.ws.RespectBinding;

import org.sec.dto.Exposer;
import org.sec.dto.SeckillExecution;
import org.sec.dto.SeckillResult;
import org.sec.entity.Seckill;
import org.sec.enums.SeckillStatEnum;
import org.sec.exception.RepeatKillException;
import org.sec.exception.SeckillCloseException;
import org.sec.exception.SeckillException;
import org.sec.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/seckill") // url:/模块/资源/{id}/细分

public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String listme(Model model) {
		// model的参数实际上是通过request转发

		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("list", seckillList);
		// list.jsp + model = ModelandView
		System.out.println("转发给li st.jsp");
		return "list";
	}
	
	
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		
		// return "forward:/hello" => 转发到能够匹配 /hello 的 controller 上
		// return "hello" => 转发jsp
		// 如果逻辑视图名是 /hello，框架还是转发到了 /WEB-INF/jsp/hello.jsp，即 /WEB-INF/jsp//hello.jsp 等同于/WEB-INF/jsp/hello.jsp。
		if (seckillId == null) {
		//对于sendRedirect如果传递的URL以”/”开头，他是相对于整个Web站点的根目录；
		//加forward可以转发给controller，对于forward如果传递的URL以”/”开头，它是相对于当前Web应用程序的根目录。
			//以 “/” 开头并且使用相对路径，那么会默认加上contextPath。
			return "redirect:/seckill/list"; // 重定向到jsp, 默认在项目后
		}
		Seckill byId = seckillService.getById(seckillId);
		System.out.println("byId: "+byId);
		if (byId == null) {
			return "forward:/seckill/list";// 加forward可以转发给controller
		}
		model.addAttribute("seckill", byId); // 把Seckill转发给jsp
		return "detail";

	}

	// ajax json
	
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result = null;

		try {
			Exposer export = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, export);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		// 为什么 trycatch 根本不会发生异常的，难道是比如连不上数据库之类的？

		return result;

	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })

	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long phone) {
		// cookie为空的话会报错，需要加给required =false 可以忽略cookie
		System.out.println("进入秒杀"+seckillId+"   "+md5+"   "+"   "+phone);
		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}

		SeckillResult<SeckillExecution> result;

		try {
			SeckillExecution se = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true, se);
		} catch (SeckillCloseException e1) {
			SeckillExecution se = new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(false, se);

		} catch (RepeatKillException e2) {
			
			SeckillExecution se = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(false, se);
		}

		catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			SeckillExecution se = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(false, se);
		}

	}
	
	
	@RequestMapping(value = "/time/now",method = RequestMethod.GET)
	
	@ResponseBody
	public SeckillResult<Long> time(){
			Date now = new Date();
			//System.out.println(System.currentTimeMillis());
//			//System.out.println(now+",,,,"+now.getTime());
			return new SeckillResult<Long>(true, now.getTime());
	}
	
	
	
	

}

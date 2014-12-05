package org.lmind.explorer.webapp.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.lmind.explorer.core.FileItem;
import org.lmind.explorer.core.FileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.UriComponents;

@Controller
public class ExplorerController extends BaseController {

	@Resource
	private FileSystem fileSystem;

	@RequestMapping(value = "/content")
	public String content(String name, String item, HttpServletResponse response) throws Exception {
		FileItem fileItem = fileSystem.getRepository(name).getFile(item);
		if (fileItem == null) {
			return null;
		}
		
		response.setContentType(fileItem.getMimeType());
		IOUtils.copy(fileItem.input(), response.getOutputStream());
		response.getOutputStream().flush();
		
		return null;
	}

	@RequestMapping(value = "/meta", produces="application/json")
	@ResponseBody
	public List<String> meta(String name) throws Exception {
		return fileSystem.getRepository(name).list();
	}
	

	@RequestMapping(value = "/list")
	public String list(String name, Model model) throws Exception {
		File[] files = fileSystem.getRoot().listFiles(f -> {
			return f.isFile() && f.getName().toLowerCase().endsWith("zip");
		});
		
		String[] o = Arrays.asList(files).stream().map(a -> {
			try {
				return a.getName();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).toArray(b -> new String[b]);
		
		model.addAttribute("list", o);
		
		return "list";
	}
	
	@RequestMapping(value = "/explore")
	public String explore(String name, Long index, Model model) {
		
		if (index == null) {
			index = 0l;
		}
		List<String> list = fileSystem.getRepository(name).list();
		
		model.addAttribute("list", list);
		model.addAttribute("index", index);
		model.addAttribute("name", name);
		
		return "explore";
	}
	
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String showlogin(String name, Long index, Model model) {
		return "login";
	}
	

	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String login(String password, HttpSession session) {
		if ("caodaniu".equals(password)) {
			session.setAttribute("login", "true");
		}
		return "redirect:list";
	}
}

package com.angular;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.angular.entity.*;
import com.angular.service.*;



@Controller
@RequestMapping("/user")
//����н�������.jsp�ļ�����/ssh1/user��ͷ��
public class UserController {
	@Resource(name="userManager")
	private IUserManager userManager;
	@Resource(name="followManager")
	private IFollowManager followManager;
	@Resource(name="favorManager")
	private IFavorManager favorManager;
	
	
//	Model����������ҳ���д�ֵ������һ��map��key��"username"��ֵ�����ﴫ���username
//	Ҫ���õ���ǰ��¼���û����ٸ���profile���������û�����ʾ����һ�����ĵĽ���	
	@RequestMapping("/update/{username}")
	public String toUpdate(@PathVariable String username, Model model){
		model.addAttribute("username", username);
		return "/updateProfile";
	}

	@RequestMapping("/updateProfile/{username}")
	public String updateProfile(User user,@PathVariable String username, Model model) {
		System.out.println(user.getPassword());
		user.setUserName(username);
		userManager.updateProfile(user);
		System.out.println("update success!");
		return toAccountInfo(username,model);
		//return "/accountInfo";
	}
	

//	ͨ��toSaveUserת��/addUser���jsp���ٵ���saveUser	
	@RequestMapping("/toSaveUser")
	public String toSaveUser(){
		return "/addUser";
	}
	
//	������addUser��jsp���������saveUser�ķ�����map������welcome.jsp��Angular.xml����ӵ�ǰ׺��׺��
	@RequestMapping("/saveUser")
	public String saveUser(User user, Model model,HttpServletRequest request){
		String username=user.getUserName();
		if (userManager.checkUserExist(user)) {
			System.out.println("userAlreadyExist");
			return "/addUser";
//			return toSaveUser();
		}
		else {
			userManager.saveUser(user);
		}
		HttpSession session=request.getSession();
		session.setAttribute("currentUser", user.getUserName());
		return toAccountInfo(username,model);
	}
	

	@RequestMapping(value="/follow/{follower}/{followed}")
	public String addFollow(@PathVariable String follower,@PathVariable String followed,
			HttpServletRequest request, Model model){
		if(follower.equals("null")){
			UserController uc= new UserController();
			return uc.login();
		}
		Follow follow=new Follow(follower,followed);
		
		if(!follower.equals(followed)){
			
			
			if(followManager.checkFollow(follow)){
				followManager.deleteFollow(follow);
				System.out.println("delete");
			}else{
				followManager.saveFollow(follow);
				System.out.println("save");
			}
		}
		
		return toProfile(followed,model);
	}


	
	@RequestMapping("/{username}")
	public String toAccountInfo(@PathVariable String username,Model model) {
//		����õ���username��null��������¼ҳ�棬ע��������Ե���controller����ķ���
		if(username.equals("null")){
			return login();
		}
		User user = userManager.findUserByUsername(username);
		int following=user.getFollowing();
		int followed=user.getFollowed();
//		�����������list����model�У�������jsp�е��ã���accountInfoҳ���г�
		List<String> books=favorManager.findFavoriteBookByUser(username);
		model.addAttribute("books", books);
		model.addAttribute("username", username);
		model.addAttribute("following", following);
		model.addAttribute("followed", followed);
		return "/accountInfo";
	}
	
	@RequestMapping("/profile/{username}")
	public String toProfile(@PathVariable String username,Model model){
		User user = userManager.findUserByUsername(username);
		int following=user.getFollowing();
		int followed=user.getFollowed();
		List<String> books=favorManager.findFavoriteBookByUser(username);
		model.addAttribute("books", books);
		model.addAttribute("otheruser", username);
		model.addAttribute("following", following);
		model.addAttribute("followed", followed);
		return "/profile";
	}

	
	
//	�ģ�
	
	
	
//	session.setAttribute("currentUser", user.getUserName());
//	��������session��attribute�Ա����ÿ��ҳ����ȡ��ǰsession���ѵ�¼��User��
//	Ҳ���԰�����User������Ϊattribute��������ʱ��controller����ȡ��ǰ��User����Ϣ

//	public interface HttpSession
//	ר��Ϊ��User��¼ʹ�õ�һ��session��The server can maintain a session in many ways such as using cookies or rewriting URLs.
//	Provides a way to identify a user across more than one page request or visit to a Web site and to store information about that user.
	
//	setAttribute(String name, Object value)
//	Binds an object to this session, using the name specified.

	@RequestMapping("/checkUser")
	public String check(User user, HttpServletRequest request){
		//System.out.println(user.getUserName());
		  
		if(userManager.checkUser(user)){
//	������ȥ��UserDAO�����checkUser������ʵ�����ǽ�������ıȶԣ�����ɹ����õ���ǰ��session������attribute����User����Ϣ
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", user.getUserName());
			return "/success";
//			����succes.jsp
		}else{
			return "/fail";
		}
	}
	
	@RequestMapping("/{username}/following")
	public String findFollowing(@PathVariable String username, Model model){
		List<String> followingList=userManager.findFollowingByUser(username);//username��follow���˵���
		for(String follow : followingList){
			System.out.println(follow);
		}
		System.out.println(username);
		model.addAttribute("followingList", followingList);
		return "/following";
	}

	@RequestMapping("/{username}/followed")
	public String findFollowed(@PathVariable String username, Model model){
		List<String> followedList=userManager.findFollowedByUser(username);
		for(String follow : followedList){
			System.out.println(follow);
		}
		System.out.println(username);
		model.addAttribute("followingList", followedList);
		return "/followed";
	}

//	jspҳ���п��Ե������ͨ��"/login" map����֮����returnһ��String��map��
	@RequestMapping("/login")
	public String login(){
		return "/login";
	}
	


}
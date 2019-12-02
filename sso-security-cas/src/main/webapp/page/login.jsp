<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
		function login() {
			$("#form").attr("action", ctxPaths + "/login");
			$("#form").submit();
		}
</script>


<div align="center">
	    <h2>自定义登录页面</h2>
	    <form id="form" method="post">
	        <label>
	            <span>用户名：</span>
	            <input type="text" name="name" />
	        </label>
	        <br/>
	        <label>
	            <span>密码：</span>
	            <input type="password" name="password" />
	        </label>
	        <input type="button" value="登录" onclick="login()"/>
	    </form>
	    ${sessionScope.message}
	</div>

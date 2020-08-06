<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2019/7/20
  Time: 6:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script type="text/javascript" src="/layui/layui.js"></script>
</head>
<body>
欢迎，这是通过Tomcat Plugin部署的项目

<form class="layui-form" action="/TestServlet" method="post" enctype="multipart/form-data">
    <div class="layui-form-item">
        <label class="layui-form-label">单行输入框</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-upload">
        <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
        <button type="button" class="layui-btn" id="test9">开始上传</button>
    </div>

    <div class="layui-upload-drag" id="test10">
        <i class="layui-icon"></i>
        <p>点击上传，或将文件拖拽到此处</p>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1" id="summit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>

</form>


</body>

<script>
    layui.use(['form', 'upload'], function () {
        var form = layui.form
            , upload = layui.upload;

        //监听提交
        form.on('submit(demo1)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return true;
        });

        //选完文件后不自动上传
        upload.render({
            elem: '#test8'
            ,url: '/upload/'
            ,auto: false
            ,accept:'file'
            //,multiple: true
            ,bindAction: '#test9'
            ,done: function(res){
                console.log(res)
            }
        });

        //拖拽上传
        upload.render({
            elem: '#test10'
            ,url: 'TestServlet'
            ,accept:'file'
            ,auto: true
            ,done: function(res){
                console.log(res)
            }
        });

    });
</script>
</html>

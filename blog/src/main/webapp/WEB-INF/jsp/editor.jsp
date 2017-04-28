<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>editor</title>
    <%@include file="/WEB-INF/jspi/main.jspi"%>
    <%@include file="/WEB-INF/jspi/editor.jspi"%>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
      <link rel="shortcut icon" href="${ctx}/images/js2.png" type="image/x-icon">
  </head>
  <body>
      <!-- 此处加载编辑器 -->
      <div id='edit'></div>
      <br/>
  </body>

  <script>
      $(function () {
          $('#edit').on('froalaEditor.initialized', function (e, editor) {
              editor.events.bindClick($('body'), '#save', function () {
                  var fs = editor.html.get();
                  editor.events.focus();
              });
          }).on('froalaEditor.initialized', function (e, editor) {
              editor.events.bindClick($('body'), '#release', function () {
                  var fs = editor.html.get();
                  $("#msgForm").submit();
                  editor.events.focus();
              });
          }).froalaEditor({
              heightMin: 400,
              heightMax: 800,
              placeholderText: '请在这里输入...',
              pastePlain: true,
              theme: 'gray',
              fontFamilySelection: true,
              fontSizeSelection: true,
              paragraphFormatSelection: true,
              tabSpaces: 2,
//              toolbarInline: true,
//              '|'表示分割线、'-'表示换行
              toolbarButtons: ['fullscreen', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'strikeThrough',
                  '|', 'fontFamily', 'fontSize', 'color', 'emoticons', '|' , 'inlineStyle',
                  'paragraphFormat',  'paragraphStyle', '-', 'align' ,'formatOL', 'formatUL','|',
                  'outdent', 'indent', 'clearFormatting', '|' , 'quote', 'insertHR', 'subscript',
                  'superscript','insertLink', 'insertImage', 'insertVideo',
                  'insertFile', 'insertTable', '|'  , 'html'],
              imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
              imageDefaultWidth: 100,
              imageInsertButtons: ['imageBack', '|', 'imageUpload', 'imageByURL'],
              imageUploadURL: '${ctx}/photo/upload.json',
              imageManagerLoadURL: '',
              fileUploadURL: '${ctx}/file/upload_file.json',
              language: 'zh_cn',
              charCounterCount: true,
              toolbarVisibleWithoutSelection: true
          });
      });

  </script>
</html>



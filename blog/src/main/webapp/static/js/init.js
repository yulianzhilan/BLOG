/*
* 每个页面的初始化方法
* 尽量不要添加一些依赖其他第三方JS库的方法
*/
$(document).ready(function() {
// Esc关闭Loading
document.onkeydown = function (e) {
if (e && e.keyCode == 27) {
closeLoading();
}
};

// 全局Ajax选项设置
$.ajaxSetup({
type : "POST",
datatype : "text/html",
timeout : 30000,
cache : false,
beforeSend: function () {
showLoading();
},
error: function () {
alert("请求失败，请稍候重试！");
},
complete : function () {
closeLoading();
}
});

// 输入法控制
$(":input.ime-off").css({"ime-mode":"disabled"});
// 自动转化大写
$('.text-uppercase').on("blur", function() { this.value = this.value.toUpperCase(); });
// 全选下拉框通过check-group分组
$(":checkbox.checklist").on("click", function () {
var group = $(this).attr("check-group");
if (!group) {
$(":checkbox:not(.checklist)").prop("checked", !!this.checked);
} else {
$(":checkbox[check-group=" +  group + "]:not(.checklist)").prop("checked", !!this.checked);
}
});
});

// 打开Loading
function showLoading() {
var gsLoader = $(".gsLoader");
if (!gsLoader || gsLoader.length == 0) {
$("body").append("<div class=\"loader gsLoader\" style=\"display: none\"><div class=\"loader-inner line-scale\"><div/><div/><div/><div/><div/></div></div>");
}
$(".gsLoader").show();

}
// 关闭Loading
function closeLoading() {
var gsLoader = $(".gsLoader");
if (!!gsLoader) {
gsLoader.hide();
}
}
// 判断ajax返回内容
// {status: 'success', errcd: '', errtx: '', result: ''}
function ajaxValidate(data) {
if (data && data.status == 'success') {
return true;
}
alert(data.errtx || '请求失败，请稍候重试！');
return false;
}
// 将数据绑定到表单上
function bindFormValue(content, data) {
$(':input', content).each(function() {
var input = $(this);
var name = input.attr('name');
var value = data[name];
input.val(value || '');
});
}
/**
 * Created by Chaos on 2016/9/18.
 */
$("body").append("<div id='cover' class='cover'><div class='loadEffect'><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></div></div>");

function showMask(){
    $('body').css("overflow","hidden");
    $("#cover").show();
}
function hideMask(){
    $('body').css("overflow","auto");
    $("#cover").hide();
}

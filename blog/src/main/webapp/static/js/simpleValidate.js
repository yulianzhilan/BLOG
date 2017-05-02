/**
 * 非空
 * <input type="text" class="required"/>
 * 字母
 * <input type="text" class="alpha"/>
 * 数字
 * <input type="text" class="num"/>
 * 字母数字
 * <input type="text" class="alphanum"/>
 * 运单号
 * <input type="text" class="awb"/>
 * 长度
 * $("#inputID").bindValidator({len: 3});                       // 3位
 * $("#inputID").bindValidator({len: {min: 5, max: 7}});        // 5-7位
 * 数值
 * $("#inputID").bindValidator({numeric: 5});                   // 5位整数
 * $("#inputID").bindValidator({numeric: {len: 8, scale: 2}});             // 8位整数2位小数
 * 正则
 * $("#inputID").bindValidator({regex: "[A-Z]{2}\\d{3,4}[A-Z]?"});
 * 范围
 * $("#inputID").bindValidator({range : {min: 5000, max: 7999}});   // 5000-7999
 * $("#inputID").bindValidator({inclusion: ["A", "B", "C"]});       // A或B或C
 */
(function ($) {
    $.simplevalidate = {};
    $.fn.bindValidator= function(options) {
        this.each(function (i, elem) {
            var $elem = $(elem);
            // 已经绑定的验证
            var optionsToUse = $elem.data("simple-validate-options") || {};
            $.extend(optionsToUse, options);
            $elem.data("simple-validate-options", optionsToUse)
        });
    };
    $.fn.formValidate = function () {
        var ret = true;
        this.each(function (i, formElem) {
            ret = $(":input", $(formElem)).validate();
            return ret;
        });
        return ret;
    };

    $.fn.validate = function () {
        var ret = true;
        var i18nMessages = $.simplevalidate.i18nMessages;
        this.each(function (i, elem) {

            var $elem = $(elem);
            // 跳过diasbled的表单元素
            if ($elem.attr("disabled")) {
                return true;
            }

            // required
            if ($elem.hasClass("required") && isBlank(elem.value)) {
                showMessage(elem, i18nMessages.required, [elem.title]);
                ret = false;
                return false;
            }

            // 没有输入，跳过其他验证
            if (isBlank(elem.value)) {
                return true;
            }

            // alpha
            if ($elem.hasClass("alpha") && !isAlpha(elem.value)) {
                showMessage(elem, i18nMessages.alpha, [elem.title]);
                ret = false;
                return false;
            }

            // num
            if ($elem.hasClass("num") && !isNum(elem.value)) {
                showMessage(elem, i18nMessages.num, [elem.title]);
                ret = false;
                return false;
            }

            // alphanum
            if ($elem.hasClass("alphanum") && !isAlphaNum(elem.value)) {
                showMessage(elem, i18nMessages.alphanum, [elem.title]);
                ret = false;
                return false;
            }

            // awb
            if ($elem.hasClass("awb") && !isAWBNumber(elem.value)) {
                showMessage(elem, i18nMessages.awb, [elem.title]);
                ret = false;
                return false;
            }

            // apt
            if ($elem.hasClass("apt") && !isAlpha(elem.value) && getLength(elem.value) != 3) {
                showMessage(elem, i18nMessages.apt, [elem.title]);
                ret = false;
                return false;
            }
            // pcs
            if ($elem.hasClass("pcs") && isNumeric(elem.value, 5)) {
                showMessage(elem, i18nMessages.pcs, [elem.title]);
                ret = false;
                return false;
            }
            // wgt
            if ($elem.hasClass("wgt") && isNumeric(elem.value, 8, 4)) {
                showMessage(elem, i18nMessages.wgt, [elem.title]);
                ret = false;
                return false;
            }

            // 获取绑定的验证配置
            var options = $elem.data("simple-validate-options") || {};

            // len
            if (!!options.len) {
                if (typeof(options.len) == "number" && getLength(elem.value) != options.len) {
                    showMessage(elem, i18nMessages.len[0], [elem.title, options.len]);
                    ret = false;
                    return false;
                }

                if (typeof(options.len) != "number" && getLength(elem.value) < options.len.min && !options.len.max) {
                    showMessage(elem, i18nMessages.len[1], [elem.title, options.len.min]);
                    ret = false;
                    return false;
                } else if (typeof(options.len) != "number" && getLength(elem.value) > options.len.max && !options.len.min) {
                    showMessage(elem, i18nMessages.len[2], [elem.title, options.len.max]);
                    ret = false;
                    return false;
                } else if (typeof(options.len) != "number" && getLength(elem.value) < options.len.min || getLength(elem.value) > options.len.max) {
                    showMessage(elem, i18nMessages.len[3], [elem.title, options.len.min, options.len.max]);
                    ret = false;
                    return false;
                }
            }

            // numeric
            if (!!options.numeric) {
                // numeric : 5
                if (typeof(options.numeric) == "number" && !isNumeric(elem.value, options.numeric)) {
                    showMessage(elem, i18nMessages.numeric[0], [elem.title, options.numeric]);
                    ret = false;
                    return false;
                }
                // numeric : {len: 5, scale: 2, minus: false}
                // 暂时不支持minus
                if (typeof(options.numeric) != "number" && !isNumeric(elem.value, options.numeric.len, options.numeric.scale)) {
                    showMessage(elem, i18nMessages.numeric[1], [elem.title, options.numeric.len, options.numeric.scale]);
                    ret = false;
                    return false;
                }
            }

            // range : {min: 1000, max: 9999}
            if (!!options.range) {
                if (elem.value < options.range.min && !options.range.max) {
                    showMessage(elem, i18nMessages.range[0], [elem.title, options.range.min]);
                    ret = false;
                    return false;
                } else if (elem.value > options.range.max && !options.range.min) {
                    showMessage(elem, i18nMessages.range[1], [elem.title, options.range.max]);
                    ret = false;
                    return false;
                } else if (elem.value < options.range.min || elem.value > options.range.max) {
                    showMessage(elem, i18nMessages.range[2], [elem.title, options.range.min, options.range.max]);
                    ret = false;
                    return false;
                }
            }

            // regex
            if (!!options.regex && !isRegex(elem.value)) {
                showMessage(elem, i18nMessages.regex, [elem.title]);
                ret = false;
                return false;
            }

            // inclusion : ['A', 'B', 'C']
            if (!!options.inclusion && !isInclusion(elem.value, options.inclusion)) {
                showMessage(elem, i18nMessages.inclusion, [elem.title]);
                ret = false;
                return false;
            }

            return true;
        });

        return ret;
    };
})(jQuery);

function showMessage(elem, failureMessage, params) {
    var failureMessage = failureMessage || "";
    if (!!params) {
        $.each(params, function(i, n) {
            failureMessage = failureMessage.replace("{" + i + "}", n);
        });
    }
    alert(failureMessage);
    elem.focus();
}
function trim(value) {
    return value.replace(/^\s+|\s+$/g, "");
}
function isBlank(value) {
    return value.replace(/^\s+|\s+$/g, "").length == 0;
}
function isAlpha(value) {
    return new RegExp("^[A-Za-z]*$", "i").test(value);
}
function isNum(value) {
    return new RegExp("^\\d*$", "i").test(value);
}
function isAlphaNum(value) {
    return new RegExp("^[A-Za-z0-9]*$", "i").test(value);
}
function isChinese(str) {
    var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
    return reg.test(str);
}
function isNumeric(str, m, n) {
    m = m || 16;        // 默认16位
    n = n || 0;
    var regex;
    if (n == 0) {
        regex = new RegExp("^[\\d]{0," + m + "}$");
    } else {
        regex = new RegExp("^[\\d]{0," + m + "}(\\.[\\d]{0," + n + "})?$");
    }
    return regex.test(str);
}
function getLength(str) {
    var wideword = true;
    var len = 0;
    if (wideword) {
        for (var i = 0; i < str.length; i++) {
            len = len + ((str.charCodeAt(i) >= 0x4e00 && str.charCodeAt(i) <= 0x9fa5) ? 2 : 1); // 汉字=2byte
        }
    } else {
        len = str.length;
    }
    return len;
}
function isRange(str, min, max, minExclude, maxExclude) {
    minExclude = minExclude || true;
    maxExclude = maxExclude || true;
    var minimum = (!min || (min == 0)) ? null : min;
    var maximum = (!max || (max == 0)) ? null : max;
    switch(true) {
        case (minimum != null && maximum != null) :
            if (str < minimum || str > maximum || (minExclude && str == minimum) || (maxExclude && str == maximum)) return false;
            break;
        case (minimum != null) :
            if (str < minimum || (minExclude && str == minimum)) return false;
            break;
        case (max != null) :
            if (str > maximum || (maxExclude && str == maximum)) return false;
            break;
    }
    return true;
}
function isRegex(str, regex) {
    return new RegExp(regex, "i").test(str);
}
function isInclusion(str, within) {
    within = within || [];
    // 忽略大小写
    if(typeof str == 'string') str = str.toUpperCase();
    for(var j = 0, length = within.length; j < length; ++j){
        var item = within[j];
        if(typeof item == 'string') item = item.toUpperCase();
        if(item == str) return true;
    }
    return false;
}
function isAWBNumber(str) {
    // 没有输入，跳过其他验证
    if (isBlank(str)) {
        return true;
    }

    // 暂时不支持X运单
    if (getLength(str) != 11) {
        return false
    }
    if (!isNum(str)) {
        return false;
    }

    // 运单号模7校验
    var number = str.substring(3);
    if (number.substring(0, 7) % 7 != number.substring(7, 8)) {
        return false;
    }
    return true;
}
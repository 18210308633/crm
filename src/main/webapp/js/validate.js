/****************************************
 *	                                    *
 *           Form窗体脚本验证            *
 *                                      *
 *      版本：    V3.3                   *
 *      修订日期：2012-08-06             *
 *                                      *
 ****************************************/

/*
*V2.0 (2008-02-14)马超修改内容
*  1、修改Form中对象的最大长度判断
*     ①去掉验证中的textarea属性
*     ②根据maxlength或maxLength属性进行判断（文本框的最大长度属性应为maxLength）
*     ③根据validrule.cn来判断汉字，一个汉字为两个字符，且最大长度小于Form中对象的实际长度时才提示，等于最大长度时不提示
*     ④文本框也可以进行最大长度的验证，通过maxLength属性，最大限度防止字符串超常引起SQL语句错误
*V2.1 马超修改内容
*  1、加入“非汉字”的验证方式（validrule.nocn）
*  2、加入“月-日”的验证方式（validrule.monthday）
*V2.2 韩林平修改内容
*  1、注释掉原2.0版本中一个汉字为两个字符的验证方式，原因：此验证方式不友好，用户还能录入但是实际已经超过录入上限。
*V3.0 (2012-02-17)马超修改内容
*  1、解决验证方法浏览器兼容性问题（Firefox等浏览器此验证函数永远返回true，等于无法进行验证，原因_elem.vmode在Firefox中无法获得vmode的值，原写法不规范只支持ie）
*     ①将所有获取对象自定义属性值的语句例如：从“_elem.vmode”改成“_elem.getAttribute("vmode")”
*     ②加入“getElement(strID)”方法用于获取对象
*V3.1 (2012-04-25)杨彦彬修改内容
*  1、添加doValidateInfo()校验方法，用于将全部不合格校验信息集中展示。保留原来逐条校验功能。
*V3.2 (2012-07-12)马超修改内容
*  1、修改float、double的验证正则表达式，原有正则表达式存在bug，原为“/^((([-\+]?\d+)(\.\d+))|(\.\d+)|(\d*))?$/”现改为“/^([-\+]?(((\d+)(\.\d+))|(\.\d+)|(\d*)))?$/”。原bug为输入“-11”或“-.5”会验证不过去。
*V3.3 (2012-08-06)马超修改内容
*  1、加入NoSingleQuotes的验证正则表达式，验证不含有单引号的字符串。
*V3.4 (2013-01-14)杨飞修改内容
*  1、加入decimal的验证正则表达式，小数点后最多3位 。
*  2、加入decimalStr的验证正则表达式，验证输入的是符合如（15,2）格式的字符串。
*     
*/
var validrule                  = new Object();
validrule.chinese              = /^([\u0391-\uFFE5|\s*]+$)?$/;
validrule.cn                   = /[^\x00-\xff]/g     	//"是汉字的正则表达式
validrule.nocn                 = /^([\x00-\xff]+$)?$/   //"是非汉字的正则表达式
validrule.cnnum                = /[^\u3447-\uFA29]/ig   //"是汉字与数字的正则表达式
validrule.english              = /^([a-zA-Z|\s*]+)?$/;
validrule.number               = /^(\d*)?$/;
validrule.decimal              = /^((\d*)\.[0-9][0-9][0-9])?$/;
validrule.integer              = /^([-\+]?\d{1,9})?$/;
validrule.float                = /^([-\+]?(((\d+)(\.\d+))|(\.\d+)|(\d*)))?$/;
validrule.double               = /^([-\+]?(((\d+)(\.\d+))|(\.\d+)|(\d*)))?$/;
validrule.string               = /^([^'<>"]+)?$/;
validrule.int                  = /^(\d{1,9})?$/;
validrule.minusint             = /^(\-([1-9])(\d*))?$/;
validrule.date                 = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]))?$/;
validrule.time                 = /^((0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))?$/;
validrule.datetime             = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]))?$/;
validrule.datehm               = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]))?$/;
validrule.year                 = /^(\d{4})?$/;
validrule.month                = /^([1-9]|0[1-9]|1[0-2])?$/;
validrule.day                  = /^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])?$/;
validrule.yearmonth            = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2]))?$/;
validrule.monthday             = /^((0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]))?$/;
validrule.postcode             = /^(\d{6})?$/;
validrule.email                = /^(.+\@.+\..+)?$/; 
validrule.phone                = /^(\(\d{3}\))?(\(?(\d{3}|\d{4}|\d{5})\)?(-?)(\d+))?((-?)(\d+))?$/;
validrule.mobiletel            = /^(01(\d{10})|1(\d{10}))?$/;
validrule.ip                   = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
validrule.idcard               = /^(\d{15}|\d{18}|\d{17}X|\d{17}x)?$/;

validrule.tabledefine          = /^(([A-Za-z])([A-Za-z0-9|_]){1,30})?$/;

validrule.NumAndStr            = /^([0-9a-zA-Z]+)?$/;
validrule.LetterStr            = /^([a-zA-Z]+)?$/;
validrule.NumStr               = /^(\d*)?$/;
validrule.allstring            = /^([^'"]+)?$/;
validrule.NoSingleQuotes       = /^([^']+)?$/;
validrule.decimalStr           = /^(([1-9]|1[0-5]),[1-2])?$/;

var validInfo     = "";
var validInfoType = "";
var validInfoArr      = new Array();


String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.LTrim = function()
{
    return this.replace(/(^\s*)/g, "");
}
String.prototype.Rtrim = function()
{
    return this.replace(/(\s*$)/g, "");
}
/**
 * 得到一个对象，解决多浏览器兼容性问题
 * strID 一般为对象的id属性也可以为对象的name属性(如果对象只有name属性则在Firefox中无法获取对象)
 */
function getElement(strID){
	return (document.getElementById(strID)||document.getElementsByName(strID)[0]);
}

function doValidate( vform )
{
	var elems = vform.elements;
	var frmLen = elems.length;
	var thePat = "";
	var strFormatInfo = "";
	//对于每一个FROM元素
	for(var i=0;i<frmLen;i++)
	{
		var _elem = elems[i];
		if(_elem.type=="application/x-shockwave-flash"){
			return true;
		}
		//为空检查
		if(_elem.getAttribute("vmode") != null && _elem.getAttribute("vmode") == "not null")
		{			
			var strValue=_elem.value;
			strValue=strValue.Trim();
			if(strValue.length == 0)
			{
				layer.msg(_elem.getAttribute("vdisp")+"不能为空!");
				_elem.focus();
				return false;
			}
		}
        //类型检查
		if(_elem.getAttribute("vtype") == null)
		{
			continue;
		}
		if(_elem.getAttribute("vtype")=="none")
		{
		   thePat = "";
		   strFormatInfo = "";
		}
		if(_elem.getAttribute("vtype")=="chinese")
		{
		   thePat = validrule.chinese;
		   strFormatInfo = "中文";
		}
		if(_elem.getAttribute("vtype")=="nocn")
		{
		   thePat = validrule.nocn;
		   strFormatInfo = "非中文的字符串";
		}
		if(_elem.getAttribute("vtype")=="cnnum")
		{
		   thePat = validrule.cnnum;
		   strFormatInfo = "中文和数字的组合！";
		}
		if(_elem.getAttribute("vtype")=="english")
		{
		   thePat = validrule.english;
		   strFormatInfo = "英文字母";
		}
		if(_elem.getAttribute("vtype")=="number")
		{
		   thePat = validrule.number;
		   strFormatInfo = "阿拉伯数字";
		}
		if(_elem.getAttribute("vtype")=="integer")
		{
		   thePat = validrule.integer;
		   strFormatInfo = "整数";
		}
		if(_elem.getAttribute("vtype")=="float")
		{
		   thePat = validrule.float;
		   strFormatInfo = "浮点数";
		}
		if(_elem.getAttribute("vtype")=="double")
		{
		   thePat = validrule.double;
		   strFormatInfo = "实数";
		}
		if(_elem.getAttribute("vtype")=="string")
		{
		   thePat = validrule.string;
		   strFormatInfo = "不含特殊符号的字符串";
		}
		if(_elem.getAttribute("vtype")=="allstring")
		{
		   thePat = validrule.allstring;
		   strFormatInfo = "不含特殊符号的字符串";
		}
		if(_elem.getAttribute("vtype")=="NoSingleQuotes")
		{
		   thePat = validrule.NoSingleQuotes;
		   strFormatInfo = "不含单引号的字符串";
		}
		if(_elem.getAttribute("vtype")=="int")
		{
		   thePat = validrule.int;
		   strFormatInfo = "正整数";
		}
		if(_elem.getAttribute("vtype")=="minusint")
		{
		   thePat = validrule.minusint;
		   strFormatInfo = "负整数，比如 -123";
		}
		if(_elem.getAttribute("vtype")=="date")
			{
		   thePat = validrule.date;
		   strFormatInfo = "日期型，比如 2004-08-12";
		}
		if(_elem.getAttribute("vtype")=="time")
		{
		   thePat = validrule.time;
		   strFormatInfo = "时间型，比如 08:37:29";
		}
		if(_elem.getAttribute("vtype")=="datehm")
			{
		   thePat = validrule.datehm;
		   strFormatInfo = "日期时分型，比如 2004-08-12 12:25";
		}
		if(_elem.getAttribute("vtype")=="datetime")
		{
		   thePat = validrule.datetime;
		   strFormatInfo = "日期时间型，比如 2004-08-12 08:37:29";
		}
		if(_elem.getAttribute("vtype")=="yearmonth")
		{
		   thePat = validrule.yearmonth;
		   strFormatInfo = "年月型，比如 2011-01";
		}
		if(_elem.getAttribute("vtype")=="monthday")
		{
		   thePat = validrule.monthday;
		   strFormatInfo = "月日型，比如 08-12";
		}
		if(_elem.getAttribute("vtype")=="year")
		{
		   thePat = validrule.year;
		   strFormatInfo = "年代格式，比如 2005";
		}
		if(_elem.getAttribute("vtype")=="month")
		{
		   thePat = validrule.month;
		   strFormatInfo = "月份格式，比如 08";
		}
		if(_elem.getAttribute("vtype")=="day")
		{
		   thePat = validrule.day;
		   strFormatInfo = "日子格式，比如 14";
		}
		if(_elem.getAttribute("vtype")=="postcode")
		{
		   thePat = validrule.postcode;
		   strFormatInfo = "邮编，比如 100001";
		}
		if(_elem.getAttribute("vtype")=="email")
		{
		   thePat = validrule.email;
		   strFormatInfo = "电子邮件格式，比如 msm@hotmail.com";
		}
		if(_elem.getAttribute("vtype")=="phone")
		{
		   thePat = validrule.phone;
		   strFormatInfo = "电话号码格式，比如 010-67891234";
		}
		if(_elem.getAttribute("vtype")=="mobiletel")
		{
		   thePat = validrule.mobiletel;
		   strFormatInfo = "手机号码格式，比如 13867891234";
		}
		if(_elem.getAttribute("vtype")=="ip")
		{
		   thePat = validrule.ip;
		   strFormatInfo = "机器ip地址格式，比如 172.22.169.11";
		}
		if(_elem.getAttribute("vtype")=="idcard")
		{
		   thePat = validrule.idcard;
		   strFormatInfo = "身份证号码，比如 15位或者18位数字";
		}
		if(_elem.getAttribute("vtype")=="tabledefine")
		{
		   thePat = validrule.tabledefine;
		   strFormatInfo = "abc_defgf";
		}
		if(_elem.getAttribute("vtype")=="LetterStr")
		{
		   thePat = validrule.LetterStr;
		   strFormatInfo = "纯字母字符串";
		}
		if(_elem.getAttribute("vtype")=="NumAndStr")
		{
		   thePat = validrule.NumAndStr;
		   strFormatInfo = "数字或字母或数字和字母的组合";
		}
		if(_elem.getAttribute("vtype")=="NumStr")
		{
		   thePat = validrule.NumStr;
		   strFormatInfo = "纯数字组成的字符串";
		}
		if(_elem.getAttribute("vtype")=="decimalStr")
		{
		   thePat = validrule.decimalStr;
		   strFormatInfo = "定义decimal类型字段时，请输入如15,2格式数据";
		}
		var gotIt = null;
		if(thePat!="")
		{
		   gotIt = thePat.exec((_elem.value).Trim());
		}
		if(gotIt == null)
		{
		   layer.msg(_elem.getAttribute("vdisp")+"输入不合法,格式应为："+strFormatInfo);
		   _elem.focus();
		   return false;
		}

		if(_elem.getAttribute("voperate")=="repeat")
		{

		   if(_elem.value != getElement(_elem.getAttribute("to")).value)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="rangeint")
		{

		   if(parseInt(_elem.value) > parseInt(_elem.max) || parseInt(_elem.value) < parseInt(_elem.min))
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="rangestr")
		{
		   if(_elem.value > _elem.max || _elem.value < _elem.min)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="comparestr")
		{
		   if(_elem.value <= getElement(_elem.getAttribute("to")).value)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="largestr")
		{
		   if(_elem.value <= getElement(_elem.getAttribute("to")).value)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeequalstr")
		{
		   if(_elem.value < getElement(_elem.getAttribute("to")).value)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="compareint")
		{
		   if(parseInt(_elem.value) <= parseInt(getElement(_elem.getAttribute("to")).value))
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeint")
		{
		   if(parseInt(_elem.value) <= parseInt(getElement(_elem.getAttribute("to")).value))
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeequalint")
		{
		   if(parseInt(_elem.value) < parseInt(getElement(_elem.getAttribute("to")).value))
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="extend")
		{
		   if((_elem.value).lastIndexOf(_elem.getAttribute("extendname"))<=0)
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}

		if(_elem.getAttribute("voperate")=="custom")
		{
		   if(!RegExp(_elem.getAttribute("regexp"),"g").test(_elem.value))
		   {
			  layer.msg(_elem.getAttribute("msg"));
			  _elem.focus();
			  return false;
		   }
		}
		//检查Form中对象的最大长度
		if(_elem.getAttribute("maxlength")!=null || _elem.getAttribute("maxLength")!=null)
		{
			//"/[^\x00-\xff]/g"是汉字的正则表达式
			//strText.length+strText.length-strText.replace(/[^\x00-\xff]/g,"").length

			var ivaluelength=_elem.value.length;
			//var ivaluelength=_elem.value.replace(validrule.cn,"  ").length;
			var imaxlength;
			if(_elem.getAttribute("maxLength")!=null)
			{
			   imaxlength=_elem.getAttribute("maxLength");		//文本框的最大长度属性应为maxLength
			}
			else{
			   imaxlength=_elem.getAttribute("maxlength");
			}
			if(ivaluelength>imaxlength)
			{
			   layer.msg(_elem.getAttribute("vdisp")+"输入的值长度太长超过了"+imaxlength+"个字符");
			   _elem.focus();
			   return false;
			}
		}
		//检查Form中对象的最小长度
		if(_elem.getAttribute("minlength")!=null || _elem.getAttribute("minLength")!=null)
		{
			//"/[^\x00-\xff]/g"是汉字的正则表达式
			//strText.length+strText.length-strText.replace(/[^\x00-\xff]/g,"").length

			var ivaluelength=_elem.value.length;
			//var ivaluelength=_elem.value.replace(validrule.cn,"  ").length;
			var iMinlength;
			if(_elem.getAttribute("maxLength")!=null)
			{
			   iMinlength=_elem.getAttribute("minlength");		//文本框的最大长度属性应为maxLength
			}
			else{
			   iMinlength=_elem.getAttribute("minLength");
			}
			if(ivaluelength<iMinlength)
			{
			   layer.msg(_elem.getAttribute("vdisp")+"输入的值长度太少低于"+iMinlength+"个字符");
			   _elem.focus();
			   return false;
			}
		}
		
	}
	
	return true;
}



function doValidateInfo( vform )
{
	var elems = vform.elements;
	var frmLen = elems.length;
	var thePat = "";
	var strFormatInfo = "";
	var iInfoArr = 0;
	//对于每一个FROM元素
	for(var i=0;i<frmLen;i++)
	{
		var _elem = elems[i];
		//为空检查
		if(_elem.getAttribute("vmode") != null && _elem.getAttribute("vmode") == "not null")
		{			
			var strValue=_elem.value;
			strValue=strValue.Trim();
			if(strValue.length == 0)
			{
				validInfo+="    ["+_elem.getAttribute("vdisp")+"]\n";
				validInfoArr[iInfoArr++] = _elem;
			}
		}
        //类型检查
		if(_elem.getAttribute("vtype") == null)
		{
			continue;
		}
		if(_elem.getAttribute("vtype")=="none")
		{
		   thePat = "";
		   strFormatInfo = "";
		}
		if(_elem.getAttribute("vtype")=="chinese")
		{
		   thePat = validrule.chinese;
		   strFormatInfo = "中文";
		}
		if(_elem.getAttribute("vtype")=="nocn")
		{
		   thePat = validrule.nocn;
		   strFormatInfo = "非中文";
		}
		if(_elem.getAttribute("vtype")=="english")
		{
		   thePat = validrule.english;
		   strFormatInfo = "英文字母";
		}
		if(_elem.getAttribute("vtype")=="number")
		{
		   thePat = validrule.number;
		   strFormatInfo = "阿拉伯数字";
		}
		if(_elem.getAttribute("vtype")=="integer")
		{
		   thePat = validrule.integer;
		   strFormatInfo = "整数";
		}
		if(_elem.getAttribute("vtype")=="float")
		{
		   thePat = validrule.float;
		   strFormatInfo = "浮点数";
		}
		if(_elem.getAttribute("vtype")=="double")
		{
		   thePat = validrule.double;
		   strFormatInfo = "实数";
		}
		if(_elem.getAttribute("vtype")=="string")
		{
		   thePat = validrule.string;
		   strFormatInfo = "不含特殊符号的字符串";
		}
		if(_elem.getAttribute("vtype")=="allstring")
		{
		   thePat = validrule.allstring;
		   strFormatInfo = "不含特殊符号的字符串";
		}
		if(_elem.getAttribute("vtype")=="NoSingleQuotes")
		{
		   thePat = validrule.NoSingleQuotes;
		   strFormatInfo = "不含单引号的字符串";
		}
		if(_elem.getAttribute("vtype")=="int")
		{
		   thePat = validrule.int;
		   strFormatInfo = "正整数";
		}
		if(_elem.getAttribute("vtype")=="minusint")
		{
		   thePat = validrule.minusint;
		   strFormatInfo = "负整数，比如 -123";
		}
		if(_elem.getAttribute("vtype")=="date")
			{
		   thePat = validrule.date;
		   strFormatInfo = "日期型，比如 2004-08-12";
		}
		if(_elem.getAttribute("vtype")=="time")
		{
		   thePat = validrule.time;
		   strFormatInfo = "时间型，比如 08:37:29";
		}
		if(_elem.getAttribute("vtype")=="datehm")
			{
		   thePat = validrule.datehm;
		   strFormatInfo = "日期时分型，比如 2004-08-12 12:25";
		}
		if(_elem.getAttribute("vtype")=="datetime")
		{
		   thePat = validrule.datetime;
		   strFormatInfo = "日期时间型，比如 2004-08-12 08:37:29";
		}
		if(_elem.getAttribute("vtype")=="yearmonth")
		{
		   thePat = validrule.yearmonth;
		   strFormatInfo = "年月型，比如 2011-01";
		}
		if(_elem.getAttribute("vtype")=="monthday")
		{
		   thePat = validrule.monthday;
		   strFormatInfo = "月日型，比如 08-12";
		}
		if(_elem.getAttribute("vtype")=="year")
		{
		   thePat = validrule.year;
		   strFormatInfo = "年代格式，比如 2005";
		}
		if(_elem.getAttribute("vtype")=="month")
		{
		   thePat = validrule.month;
		   strFormatInfo = "月份格式，比如 08";
		}
		if(_elem.getAttribute("vtype")=="day")
		{
		   thePat = validrule.day;
		   strFormatInfo = "日子格式，比如 14";
		}
		if(_elem.getAttribute("vtype")=="postcode")
		{
		   thePat = validrule.postcode;
		   strFormatInfo = "邮编，比如 100001";
		}
		if(_elem.getAttribute("vtype")=="email")
		{
		   thePat = validrule.email;
		   strFormatInfo = "电子邮件格式，比如 msm@hotmail.com";
		}
		if(_elem.getAttribute("vtype")=="phone")
		{
		   thePat = validrule.phone;
		   strFormatInfo = "电话号码格式，比如 010-67891234";
		}
		if(_elem.getAttribute("vtype")=="mobiletel")
		{
		   thePat = validrule.mobiletel;
		   strFormatInfo = "手机号码格式，比如 13867891234";
		}
		if(_elem.getAttribute("vtype")=="ip")
		{
		   thePat = validrule.ip;
		   strFormatInfo = "机器ip地址格式，比如 172.22.169.11";
		}
		if(_elem.getAttribute("vtype")=="idcard")
		{
		   thePat = validrule.idcard;
		   strFormatInfo = "身份证号码，比如 15位或者18位数字";
		}
		if(_elem.getAttribute("vtype")=="tabledefine")
		{
		   thePat = validrule.tabledefine;
		   strFormatInfo = "abc_defgf";
		}
		if(_elem.getAttribute("vtype")=="LetterStr")
		{
		   thePat = validrule.LetterStr;
		   strFormatInfo = "纯字母字符串";
		}
		if(_elem.getAttribute("vtype")=="NumAndStr")
		{
		   thePat = validrule.NumAndStr;
		   strFormatInfo = "数字和字母字符串";
		}
		if(_elem.getAttribute("vtype")=="NumStr")
		{
		   thePat = validrule.NumStr;
		   strFormatInfo = "纯数字组成的字符串";
		}

		var gotIt = null;
		if(thePat!="")
		{
		   gotIt = thePat.exec((_elem.value).Trim());
		}
		if(gotIt == null)
		{
		   validInfoType +="    ["+_elem.getAttribute("vdisp")+"]输入不合法,格式应为："+strFormatInfo+"\n";
		   validInfoArr[iInfoArr++] = _elem;
		}

		if(_elem.getAttribute("voperate")=="repeat")
		{

		   if(_elem.value != getElement(_elem.getAttribute("to")).value)
		   {
				  	validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
				  	validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="rangeint")
		{

		   if(parseInt(_elem.value) > parseInt(_elem.max) || parseInt(_elem.value) < parseInt(_elem.min))
		   {
					  validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
					  validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="rangestr")
		{
		   if(_elem.value > _elem.max || _elem.value < _elem.min)
		   {
						validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
						validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="comparestr")
		{
		   if(_elem.value <= getElement(_elem.getAttribute("to")).value)
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="largestr")
		{
		   if(_elem.value <= getElement(_elem.getAttribute("to")).value)
		   {
						validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
						validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeequalstr")
		{
		   if(_elem.value < getElement(_elem.getAttribute("to")).value)
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="compareint")
		{
		   if(parseInt(_elem.value) <= parseInt(getElement(_elem.getAttribute("to")).value))
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeint")
		{
		   if(parseInt(_elem.value) <= parseInt(getElement(_elem.getAttribute("to")).value))
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="largeequalint")
		{
		   if(parseInt(_elem.value) < parseInt(getElement(_elem.getAttribute("to")).value))
		   {
						validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
						validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="extend")
		{
		   if((_elem.value).lastIndexOf(_elem.getAttribute("extendname"))<=0)
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}

		if(_elem.getAttribute("voperate")=="custom")
		{
		   if(!RegExp(_elem.getAttribute("regexp"),"g").test(_elem.value))
		   {
			  		validInfoType +="    ["+_elem.getAttribute("msg")+"]\n";
			  		validInfoArr[iInfoArr++] = _elem;
		   }
		}
		//检查Form中对象的最大长度
		if(_elem.getAttribute("maxlength")!=null || _elem.getAttribute("maxLength")!=null)
		{
			//"/[^\x00-\xff]/g"是汉字的正则表达式
			//strText.length+strText.length-strText.replace(/[^\x00-\xff]/g,"").length

			var ivaluelength=_elem.value.length;
			//var ivaluelength=_elem.value.replace(validrule.cn,"  ").length;
			var imaxlength;
			if(_elem.getAttribute("maxLength")!=null)
			{
			   imaxlength=_elem.getAttribute("maxLength");		//文本框的最大长度属性应为maxLength
			}
			else{
			   imaxlength=_elem.getAttribute("maxlength");
			}
			if(ivaluelength>imaxlength)
			{
			   validInfoType +="    ["+_elem.getAttribute("vdisp")+"]输入的值长度太长超过了"+imaxlength+"个字符\n";
			   validInfoArr[iInfoArr++] = _elem;
			}
		}
	}
	
	
	if(validInfoType.length!=0 && validInfo.length!=0){
			layer.msg("不能为空的信息是\n"+validInfo+"\n类型错误的信息是\n"+validInfoType);
			validInfo="";
			validInfoType="";
			validInfoShow(validInfoArr);
			return false;
	}else if(validInfoType.length ==0 && validInfo.length!=0){
			layer.msg("不能为空的信息是\n"+validInfo);
			validInfo="";
			validInfoType="";
			validInfoShow(validInfoArr);
			return false;
	}else if(validInfoType.length!=0 && validInfo.length == 0){
			layer.msg("类型错误的信息是\n"+validInfoType);
			validInfo="";
			validInfoType="";
			validInfoShow(validInfoArr);
			return false;
	}
		
	return true;
}

function validInfoShow(){
	for(var i=0;i<validInfoArr.length;i++)
  {
  		var _elem = validInfoArr[i];
			if(_elem.tagName == 'INPUT')
			{
					switch(_elem.type)
					{
						case 'text': _elem.style.backgroundColor='#FFFFCC';break;
						case 'file': _elem.style.backgroundColor='#FFFFCC';break;
						case 'checkbox': _elem.style.backgroundColor='#FFFFCC';break;
					}
			}else if(_elem.tagName == 'SELECT')
			{
					_elem.style.backgroundColor='#FFFFCC';
			}else if(_elem.tagName == 'TEXTAREA')
			{
					_elem.style.backgroundColor='#FFFFCC';
			}
	}
}		

//以下为textarea标签限制最大长度功能
function onmyinput(o,maxLength)
{
 if(o.value.length>= maxLength)
 {
  if(o.value.length> maxLength)
   o.value = o.value.substring(0,maxLength);
  return false;
 }
 return true;
}
function mygetclipdata()
{
 if(!document.all)
 {
  netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
  var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
  var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
  trans.addDataFlavor('text/unicode');
  var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
  clip.getData(trans,clip.kGlobalClipboard);
  var str=new Object();
  var strLength=new Object();
  trans.getTransferData("text/unicode",str,strLength); 
  if (str)
  str=str.value.QueryInterface(Components.interfaces.nsISupportsString);
  var pastetext;
  if (str)
  pastetext=str.data.substring(0,strLength.value / 2);
  return pastetext;
 }
 else
 {
  return window.clipboardData.getData("Text");
 }
}
function mysetclipdata(o)
{
 if(!document.all)
 {
  netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
  var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
  var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
  trans.addDataFlavor("text/unicode");
  var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
  str.data=o;
  trans.setTransferData("text/unicode",str,o.length*2);
  var clipid=Components.interfaces.nsIClipboard;
  clip.setData(trans,null,clipid.kGlobalClipboard);
 }
 else
 {
  window.clipboardData.setData("Text",o);
 }
}
function onmypaste(o, maxLength)
{
 var nMaxLen=o.getAttribute? parseInt(maxLength):"";
 
 if(!document.all)
 {
  //layer.msg("不可能执行的代码");
 }
 else
 {

  if(document.selection.createRange().text.length>0)
  {
   var ovalueandclipboarddata = o.value +window.clipboardData.getData("Text");
   if(o.getAttribute && ovalueandclipboarddata.length-document.selection.createRange().text.length>nMaxLen)
   {
    if(window.clipboardData.getData("Text").substring(0,document.selection.createRange().text.length+nMaxLen-o.value.length)!="")
     window.clipboardData.setData("Text",window.clipboardData.getData("Text").substring(0,document.selection.createRange().text.length+nMaxLen-o.value.length));
    else
     return false;
   }
  }
  else
  {
   var ovalueandclipboarddata = o.value +window.clipboardData.getData("Text");
   if(o.getAttribute && ovalueandclipboarddata.length>nMaxLen)
   {
    if(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length)!="")
     window.clipboardData.setData("Text",ovalueandclipboarddata.substring(0,nMaxLen-o.value.length));
    else
     return false;
   }
  }
  return true;
 }
}
function onmykeypress(o, maxLength)
{
 if(!document.all)
 {
  var nMaxLen=o.getAttribute? parseInt(maxLength):"";

  if(onmykeypress.caller.arguments[0].ctrlKey == true)
  {
   if(onmykeypress.caller.arguments[0].which==118)
   {

    if(o.selectionStart<o.selectionEnd)
    {
     var ovalueandclipboarddata = o.value + mygetclipdata();
     if(o.getAttribute && (ovalueandclipboarddata.length-o.selectionEnd + o.selectionStart>nMaxLen))
     {
      if(mygetclipdata().substring(0,o.selectionEnd - o.selectionStart+nMaxLen-o.value.length)!="")
       mysetclipdata(mygetclipdata().substring(0,o.selectionEnd - o.selectionStart+nMaxLen-o.value.length));
      else
       return false;
     }
    }
    else
    {
     var ovalueandclipboarddata = o.value +mygetclipdata();
     if(o.getAttribute && ovalueandclipboarddata.length>nMaxLen)
     {
      if(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length)!="")
       mysetclipdata(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length));
      else
       return false;
     }
    }
    return true;

   }
  }


  if(onmykeypress.caller.arguments[0].which==0 || onmykeypress.caller.arguments[0].which==8)
   return true;
  if(o.value.length>= maxLength)
  {
   if(o.selectionStart<o.selectionEnd)
    return true;
   if(o.value.length> maxLength)
    o.value = o.value.substring(0, maxLength);
   return false;
  }
  else
   return true;
 
 }
 else
 {
  if(document.selection.createRange().text.length>0)
   return true;
  if(o.value.length>= maxLength)
   return false;
  else
   return true;
 }
}
window.onload = init_MaxLength;
function init_MaxLength () 
{
  var textAreaObj = document.getElementsByTagName("textarea");
  var maxLength;
  for (var i = 0; i < textAreaObj.length; i++) {
      maxLength = textAreaObj[i].getAttribute("maxLength") == null ? 0 : textAreaObj[i].getAttribute("maxLength");
      if (maxLength == 0) continue;
          textAreaObj[i].onpaste = function(){ return onmypaste(this,maxLength)};
          textAreaObj[i].onkeypress = function(){ return onmykeypress(this,maxLength)};
          textAreaObj[i].onpropertychange = function(){ onmyinput(this,maxLength)};
        if(!document.all){
          textAreaObj[i].setAttribute("oninput","return onmyinput(this," + maxLength + ")");
        }
  }
}
/**
 * 日期格式化为字符串
 * @param fmt
 * @returns
 */
Date.prototype.Format = function(fmt) {
	//补全3位
	function checkTime(i){
	    if (i<100 && i>9){
	        i = "0" + i;
	    }
	    if (i<10){
	        i = "00" + i;
	    }
	    return i;
	}
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : checkTime(this.getMilliseconds())
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 生成主键
 */
function getSequence(){
	return (new Date()).Format("yyyyMMddhhmmssS")+parseInt(Math.random()*(999999-100000+1)+100000); 
}
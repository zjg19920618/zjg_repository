<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="secondaryMenu map_top_menu">
	<ul class="nav">
		<li>唐山市</li>
		<li class="map_districtCounty">
			<select class="form-control form-controlIndex" value="区/县" style="height:40px;line-height:40px;">
				<option value="区/县" data-img="" style="background:#fff;">区/县</option>
			</select>
		</li>
		<li class="map_bank_branch">
			<select class="form-control form-controlIndexEq" value="支行名称" style="height:40px;line-height:40px;">
				<option value="支行名称" data-code="" data-img="" style="background:#fff;">支行名称</option>
			</select>
		</li>
		<li class="map_machine_status" style="width: 100px;">
			<select class="form-control form-controlIndexTh" value="00" style="width: 100px;height:40px;line-height:40px;">
				<option value="00" style="background:#fff;">请选择</option>
				<option value="01" style="background:#fff;">全部</option>
				<option value="02" style="background:#fff;">正常</option>
				<option value="03" style="background:#fff;">异常</option>
			</select>
		</li>
	</ul>
</div>
<div class="total_map">
<!--  
	<div class="map_left" style="display:none;">
		<div class="for_menu_bg">
			<div class="map_title">唐山市</div>
			<div class="for_menu_height">
				<ul class="total_map_menu map_left_menu">
				</ul>
			</div>
		</div>
	</div>
-->
	<div class="map_right">
		<div class="map_right_in">
			<div class="mapTimeDate">更新时间：<span id="mapTimeDate1"></span></div>
			<div class="city_pool">
				<!-- 唐山市辖区分布 -->
				<div class="city_common city_main">
					<img src="../../img/map/zhinanzhen.png" alt/>
					<img src="../../img/map/city_qxx.png" alt data-img="city_qxx"/>
					<img src="../../img/map/city_cfdq.png" alt data-img="city_cfdq"/>
					<img src="../../img/map/city_fnq.png" alt data-img="city_fnq"/>
					<img src="../../img/map/city_frq.png" alt data-img="city_frq"/>
					<img src="../../img/map/city_gyq.png" alt data-img="city_gyq"/>
					<img src="../../img/map/city_kpq.png" alt data-img="city_kpq"/>
					<img src="../../img/map/city_lbq.png" alt data-img="city_lbq"/>
					<img src="../../img/map/city_lnq.png" alt data-img="city_lnq"/>
					<img src="../../img/map/city_lnx.png" alt data-img="city_lnx"/>
					<img src="../../img/map/city_ltkfq.png" alt data-img="city_ltkfq"/>
					<img src="../../img/map/city_ltx.png" alt data-img="city_ltx"/>
					<img src="../../img/map/city_lx.png" alt data-img="city_lx"/>
					<img src="../../img/map/city_qas.png" alt data-img="city_qas"/>
					<img src="../../img/map/city_ytx.png" alt data-img="city_ytx"/>
					<img src="../../img/map/city_zhs.png" alt data-img="city_zhs"/>
					<img src="../../img/map/map_blank.png" alt usemap="#map_blank"/>
					<map name="map_blank">
						<area shape="rect" coords="220,156,281,172" alt="city_zhs"/>
						<area shape="rect" coords="369,104,430,120" alt="city_qxx"/>
						<area shape="rect" coords="377,586,451,602" alt="city_cfdq"/>
						<area shape="rect" coords="259,483,320,499" alt="city_fnq"/>
						<area shape="rect" coords="262,296,323,312" alt="city_frq"/>
						<area shape="rect" coords="393,343,454,359" alt="city_gyq"/>
						<area shape="rect" coords="344,363,405,379" alt="city_kpq"/>
						<area shape="rect" coords="304,386,365,402" alt="city_lbq"/>
						<area shape="rect" coords="319,414,380,430" alt="city_lnq"/>
						<area shape="rect" coords="453,466,514,482" alt="city_lnx"/>
						<area shape="rect" coords="96,509,183,525" alt="city_ltkfq"/>
						<area shape="rect" coords="574,529,635,545" alt="city_ltx"/>
						<area shape="rect" coords="487,331,548,347" alt="city_lx"/>
						<area shape="rect" coords="483,200,544,216" alt="city_qas"/>
						<area shape="rect" coords="127,301,188,317" alt="city_ytx"/>
					</map>
				</div>
				<!-- 遵化市网点分布 -->
				<div class="city_common city_zhs city_detail">
					<img src="../../img/map/city_zhs/city_zhs.png" alt/>
					<img src="../../img/map/city_zhs/city_zhs_zhzh.png" alt data-img="city_zhs_zhzh"/>
					<img src="../../img/map/city_zhs/city_zhs_zhflczh.png" alt data-img="city_zhs_zhflczh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_zhs"/>
					<map name="city_zhs">
						<area shape="rect" coords="482,266,498,282" alt="city_zhs_zhzh" title="遵化支行"/>
						<area shape="rect" coords="470,232,486,248" alt="city_zhs_zhflczh" title="遵化富力城支行"/>
					</map>
				</div>
				<!-- 迁西县网点分布 -->
				<div class="city_common city_qxx city_detail">
					<img src="../../img/map/city_qxx/city_qxx.png" alt/>
					<img src="../../img/map/city_qxx/city_qxx_qxzh.png" alt data-img="city_qxx_qxzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_qxx"/>
					<map name="city_qxx">
						<area shape="rect" coords="337,431,353,447" alt="city_qxx_qxzh" title="迁西支行"/>
					</map>
				</div>
				<!-- 迁安市网点分布 -->
				<div class="city_common city_qas city_detail">
					<img src="../../img/map/city_qas/city_qas.png" alt/>
					<img src="../../img/map/city_qas/city_qas_qazh.png" alt data-img="city_qas_qazh"/>
					<img src="../../img/map/city_qas/city_qas_qaxyzh.png" alt data-img="city_qas_qaxyzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_qas"/>
					<map name="city_qas">
						<area shape="rect" coords="444,407,460,423" alt="city_qas_qazh" title="迁安支行"/>
						<area shape="rect" coords="435,430,451,449" alt="city_qas_qaxyzh" title="迁安馨园支行"/>
					</map>
				</div>				<!-- 曹妃甸区网点分布 -->
				<div class="city_common city_cfdq city_detail">
					<img src="../../img/map/city_cfdq/city_cfdq.png" alt/>
					<img src="../../img/map/city_cfdq/city_cfdq_cfdzh.png" alt data-img="city_cfdq_cfdzh"/>
					<img src="../../img/map/city_cfdq/city_cfdq_cfdgyqzh.png" alt data-img="city_cfdq_cfdgyqzh"/>
					<img src="../../img/map/city_cfdq/city_cfdq_nbzh.png" alt data-img="city_cfdq_nbzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_cfdq"/>
					<map name="city_cfdq">
						<area shape="rect" coords="432,205,448,221" alt="city_cfdq_cfdzh" title="曹妃甸支行"/>
						<area shape="rect" coords="450,468,466,484" alt="city_cfdq_cfdgyqzh" title="曹妃甸工业区支行"/>
						<area shape="rect" coords="215,251,231,267" alt="city_cfdq_nbzh" title="南堡支行"/>
					</map>
				</div>
				<!-- 丰南区网点分布 -->
				<div class="city_common city_fnq city_detail">
					<img src="../../img/map/city_fnq/city_fnq.png" alt/>
					<img src="../../img/map/city_fnq/city_fnq_fnhfsqzh.png" alt data-img="city_fnq_fnhfsqzh"/>
					<img src="../../img/map/city_fnq/city_fnq_fnzh.png" alt data-img="city_fnq_fnzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_fnq"/>
					<map name="city_fnq">
						<area shape="rect" coords="380,128,396,144" alt="city_fnq_fnhfsqzh" title="丰南惠丰社区支行"/>
						<area shape="rect" coords="397,118,413,134" alt="city_fnq_fnzh" title="丰南支行"/>
					</map>
				</div>
				<!-- 丰润区网点分布 -->
				<div class="city_common city_frq city_detail">
					<img src="../../img/map/city_frq/city_frq.png" alt/>
					<img src="../../img/map/city_frq/city_frq_gysqzh.png" alt data-img="city_frq_gysqzh"/>
					<img src="../../img/map/city_frq/city_frq_frzh.png" alt data-img="city_frq_frzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_frq"/>
					<map name="city_frq">
						<area shape="rect" coords="461,319,477,335" alt="city_frq_gysqzh" title="浭阳社区支行"/>
						<area shape="rect" coords="496,311,512,327" alt="city_frq_frzh" title="丰润支行"/>
					</map>
				</div>
				<!-- 古冶区网点分布 -->
				<div class="city_common city_gyq city_detail">
					<img src="../../img/map/city_gyq/city_gyq.png" alt/>
					<img src="../../img/map/city_gyq/city_gyq_lxzh.png" alt data-img="city_gyq_lxzh"/>
					<img src="../../img/map/city_gyq/city_gyq_zgzzh.png" alt data-img="city_gyq_zgzzh"/>
					<img src="../../img/map/city_gyq/city_gyq_gyzh.png" alt data-img="city_gyq_gyzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_gyq"/>
					<map name="city_gyq">
						<area shape="rect" coords="430,292,446,308" alt="city_gyq_lxzh" title="林西支行"/>
						<area shape="rect" coords="339,148,355,164" alt="city_gyq_zgzzh" title="赵各庄支行"/>
						<area shape="rect" coords="397,252,413,268" alt="city_gyq_gyzh" title="古冶支行"/>
					</map>
				</div>
				<!-- 开平区网点分布 -->
				<div class="city_common city_kpq city_detail">
					<img src="../../img/map/city_kpq/city_kpq.png" alt/>
					<img src="../../img/map/city_kpq/city_kpq_lnzh.png" alt data-img="city_kpq_lnzh"/>
					<img src="../../img/map/city_kpq/city_kpq_mjgzh.png" alt data-img="city_kpq_mjgzh"/>
					<img src="../../img/map/city_kpq/city_kpq_xylzh.png" alt data-img="city_kpq_xylzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_kpq"/>
					<map name="city_kpq">
						<area shape="rect" coords="343,457,359,473" alt="city_kpq_lnzh" title="路南支行"/>
						<area shape="rect" coords="348,273,364,289" alt="city_kpq_mjgzh" title="马家沟支行"/>
						<area shape="rect" coords="390,363,406,379" alt="city_kpq_xylzh" title="新苑路支行"/>
					</map>
				</div>
				<!-- 路北区网点分布 -->
				<div class="city_common city_lbq city_detail">
					<img src="../../img/map/city_lbq/city_lbq.png" alt/>
					<img src="../../img/map/city_lbq/city_lbq_lgzh.png" alt data-img="city_lbq_lgzh"/>
					<img src="../../img/map/city_lbq/city_lbq_gmzh.png" alt data-img="city_lbq_gmzh"/>
					<img src="../../img/map/city_lbq/city_lbq_fhzh.png" alt data-img="city_lbq_fhzh"/>
					<img src="../../img/map/city_lbq/city_lbq_yyzh.png" alt data-img="city_lbq_yyzh"/>
					<img src="../../img/map/city_lbq/city_lbq_wzzh.png" alt data-img="city_lbq_wzzh"/>
					<img src="../../img/map/city_lbq/city_lbq_xylzh.png" alt data-img="city_lbq_xylzh"/>
					<img src="../../img/map/city_lbq/city_lbq_tyzh.png" alt data-img="city_lbq_tyzh"/>
					<img src="../../img/map/city_lbq/city_lbq_tczh.png" alt data-img="city_lbq_tczh"/>
					<img src="../../img/map/city_lbq/city_lbq_wtzh.png" alt data-img="city_lbq_wtzh"/>
					<img src="../../img/map/city_lbq/city_lbq_yhzh.png" alt data-img="city_lbq_yhzh"/>
					<img src="../../img/map/city_lbq/city_lbq_cndzh.png" alt data-img="city_lbq_cndzh"/>
					<img src="../../img/map/city_lbq/city_lbq_hjlzh.png" alt data-img="city_lbq_hjlzh"/>
					<img src="../../img/map/city_lbq/city_lbq_hyzh.png" alt data-img="city_lbq_hyzh"/>
					<img src="../../img/map/city_lbq/city_lbq_lzlzh.png" alt data-img="city_lbq_lzlzh"/>
					<img src="../../img/map/city_lbq/city_lbq_gylzh.png" alt data-img="city_lbq_gylzh"/>
					<img src="../../img/map/city_lbq/city_lbq_hdzh.png" alt data-img="city_lbq_hdzh"/>
					<img src="../../img/map/city_lbq/city_lbq_spzh.png" alt data-img="city_lbq_spzh"/>
					<img src="../../img/map/city_lbq/city_lbq_cydzh.png" alt data-img="city_lbq_cydzh"/>
					<img src="../../img/map/city_lbq/city_lbq_wglzh.png" alt data-img="city_lbq_wglzh"/>
					<img src="../../img/map/city_lbq/city_lbq_jclzh.png" alt data-img="city_lbq_jclzh"/>
					<img src="../../img/map/city_lbq/city_lbq_jslzh.png" alt data-img="city_lbq_jslzh"/>
					<img src="../../img/map/city_lbq/city_lbq_whlzh.png" alt data-img="city_lbq_whlzh"/>
					<img src="../../img/map/city_lbq/city_lbq_lbzh.png" alt data-img="city_lbq_lbzh"/>
					<img src="../../img/map/city_lbq/city_lbq_bxzh.png" alt data-img="city_lbq_bxzh"/>
					<img src="../../img/map/city_lbq/city_lbq_xsdzh.png" alt data-img="city_lbq_xsdzh"/>
					<img src="../../img/map/city_lbq/city_lbq_xhzh.png" alt data-img="city_lbq_xhzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_lbq"/>
					<map name="city_lbq">
						<area shape="rect" coords="302,342,316,358" alt="city_lbq_lgzh" title="鹭港支行"/>
						<area shape="rect" coords="321,395,337,411" alt="city_lbq_gmzh" title="光明支行"/>
						<area shape="rect" coords="325,358,341,374" alt="city_lbq_fhzh" title="凤凰支行"/>
						<area shape="rect" coords="375,386,391,402" alt="city_lbq_yyzh" title="友谊支行"/>
						<area shape="rect" coords="411,394,427,410" alt="city_lbq_wzzh" title="旺座支行"/>
						<area shape="rect" coords="429,370,445,386" alt="city_lbq_xylzh" title="学院路支行"/>
						<area shape="rect" coords="388,344,404,360" alt="city_lbq_tyzh" title="天元支行"/>
						<area shape="rect" coords="388,297,404,313" alt="city_lbq_tczh" title="唐城支行"/>
						<area shape="rect" coords="377,267,393,283" alt="city_lbq_wtzh" title="梧桐支行"/>
						<area shape="rect" coords="437,241,453,257" alt="city_lbq_yhzh" title="裕华支行"/>
						<area shape="rect" coords="487,204,503,220" alt="city_lbq_cndzh" title="长宁道支行"/>
						<area shape="rect" coords="515,156,531,172" alt="city_lbq_hjlzh" title="火炬路支行"/>
						<area shape="rect" coords="581,109,597,125" alt="city_lbq_hyzh" title="河茵支行"/>
						<area shape="rect" coords="555,176,571,192" alt="city_lbq_lzlzh" title="龙泽路支行"/>
						<area shape="rect" coords="640,183,656,199" alt="city_lbq_gylzh" title="缸窑路支行"/>
						<area shape="rect" coords="626,236,642,252" alt="city_lbq_hdzh" title="河东支行"/>
						<area shape="rect" coords="557,202,573,218" alt="city_lbq_spzh" title="尚品支行"/>
						<area shape="rect" coords="545,267,561,283" alt="city_lbq_cydzh" title="朝阳道支行"/>
						<area shape="rect" coords="462,292,478,308" alt="city_lbq_wglzh" title="卫国路支行"/>
						<area shape="rect" coords="481,313,497,329" alt="city_lbq_jclzh" title="机场路支行"/>
						<area shape="rect" coords="500,307,516,323" alt="city_lbq_jslzh" title="建设路支行"/>
						<area shape="rect" coords="534,336,550,352" alt="city_lbq_whlzh" title="文化路支行"/>
						<area shape="rect" coords="482,360,498,376" alt="city_lbq_lbzh" title="路北支行"/>
						<area shape="rect" coords="498,372,514,388" alt="city_lbq_bxzh" title="北新支行"/>
						<area shape="rect" coords="518,398,534,414" alt="city_lbq_xsdzh" title="西山道支行"/>
						<area shape="rect" coords="535,411,551,427" alt="city_lbq_xhzh" title="新华支行"/>
					</map>
				</div>
				<!-- 路南区网点分布 -->
				<div class="city_common city_lnq city_detail">
					<img src="../../img/map/city_lnq/city_lnq.png" alt/>
					<img src="../../img/map/city_lnq/city_lnq_hmzh.png" alt data-img="city_lnq_hmzh"/>
					<img src="../../img/map/city_lnq/city_lnq_dllzh.png" alt data-img="city_lnq_dllzh"/>
					<img src="../../img/map/city_lnq/city_lnq_hynlzh.png" alt data-img="city_lnq_hynlzh"/>
					<img src="../../img/map/city_lnq/city_lnq_yyb.png" alt data-img="city_lnq_yyb"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_lnq"/>
					<map name="city_lnq">
						<area shape="rect" coords="138,228,154,244" alt="city_lnq_hmzh" title="惠民支行"/>
						<area shape="rect" coords="221,137,237,153" alt="city_lnq_dllzh" title="大里路支行"/>
						<area shape="rect" coords="357,97,373,113" alt="city_lnq_hynlzh" title="华岩南路支行"/>
						<area shape="rect" coords="251,80,267,96" alt="city_lnq_yyb" title="营业部"/>
					</map>
				</div>
				<!-- 滦南县网点分布 -->
				<div class="city_common city_lnx city_detail">
					<img src="../../img/map/city_lnx/city_lnx.png" alt/>
					<img src="../../img/map/city_lnx/city_lnx_lnzh.png" alt data-img="city_lnx_lnzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_lnx"/>
					<map name="city_lnx">
						<area shape="rect" coords="453,156,469,172" alt="city_lnx_lnzh" title="滦南支行"/>
					</map>
				</div>
				<!-- 芦台开发区网点分布 -->
				<div class="city_common city_ltkfq city_detail">
					<img src="../../img/map/city_ltkfq/city_ltkfq.png" alt/>
					<img src="../../img/map/city_ltkfq/city_ltkfq_ltzh.png" alt data-img="city_ltkfq_ltzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_ltkfq"/>
					<map name="city_ltkfq">
						<area shape="rect" coords="547,342,563,358" alt="city_ltkfq_ltzh" title="芦台支行"/>
					</map>
				</div>
				<!-- 乐亭县网点分布 -->
				<div class="city_common city_ltx city_detail">
					<img src="../../img/map/city_ltx/city_ltx.png" alt/>
					<img src="../../img/map/city_ltx/city_ltx_ltzh.png" alt data-img="city_ltx_ltzh"/>
					<img src="../../img/map/city_ltx/city_ltx_hgzh.png" alt data-img="city_ltx_hgzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_ltx"/>
					<map name="city_ltx">
						<area shape="rect" coords="323,224,339,240" alt="city_ltx_ltzh" title="乐亭支行"/>
						<area shape="rect" coords="416,502,432,518" alt="city_ltx_hgzh" title="海港支行"/>
					</map>
				</div>
				<!-- 滦县网点分布 -->
				<div class="city_common city_lx city_detail">
					<img src="../../img/map/city_lx/city_lx.png" alt/>
					<img src="../../img/map/city_lx/city_lx_lxzh.png" alt data-img="city_lx_lxzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_lx"/>
					<map name="city_lx">
						<area shape="rect" coords="623,402,639,418" alt="city_lx_lxzh" title="滦县支行"/>
					</map>
				</div>
				<!-- 玉田县网点分布 -->
				<div class="city_common city_ytx city_detail">
					<img src="../../img/map/city_ytx/city_ytx.png" alt/>
					<img src="../../img/map/city_ytx/city_ytx_ytzh.png" alt data-img="city_ytx_ytzh"/>
					<img src="../../img/map/city_ytx/city_ytx_ytyxzh.png" alt data-img="city_ytx_ytyxzh"/>
					<img src="../../img/map/map_blank.png" alt usemap="#city_ytx"/>
					<map name="city_ytx">
						<area shape="rect" coords="428,155,444,171" alt="city_ytx_ytzh" title="玉田支行"/>
						<area shape="rect" coords="411,171,427,187" alt="city_ytx_ytyxzh" title="玉田玉兴支行"/>
					</map>
				</div>
				<div class="bank_detail_common city_qas_qazh"></div>
				<div class="bank_detail_common city_qas_qaxyzh"></div>
				<div class="bank_detail_common city_zhs_zhzh"></div>
				<div class="bank_detail_common city_zhs_zhflczh"></div>
				<div class="bank_detail_common city_qxx_qxzh"></div>
				<div class="bank_detail_common city_ytx_ytzh"></div>
				<div class="bank_detail_common city_ytx_ytyxzh"></div>
				<div class="bank_detail_common city_lnx_lnzh"></div>
				<div class="bank_detail_common city_cfdq_cfdzh"></div>
				<div class="bank_detail_common city_cfdq_cfdgyqzh"></div>
				<div class="bank_detail_common city_cfdq_nbzh"></div>
				<div class="bank_detail_common city_lx_lxzh"></div>
				<div class="bank_detail_common city_ltx_ltzh"></div>
				<div class="bank_detail_common city_ltx_hgzh"></div>
				<div class="bank_detail_common city_lnq_yyb"></div>
				<div class="bank_detail_common city_lnq_hynlzh"></div>
				<div class="bank_detail_common city_lnq_dllzh"></div>
				<div class="bank_detail_common city_lnq_hmzh"></div>
				<div class="bank_detail_common city_lbq_lbzh"></div>
				<div class="bank_detail_common city_lbq_jclzh"></div>
				<div class="bank_detail_common city_lbq_xsdzh"></div>
				<div class="bank_detail_common city_lbq_whlzh"></div>
				<div class="bank_detail_common city_lbq_lgzh"></div>
				<div class="bank_detail_common city_lbq_tyzh"></div>
				<div class="bank_detail_common city_lbq_xhzh"></div>
				<div class="bank_detail_common city_lbq_yyzh"></div>
				<div class="bank_detail_common city_lbq_gmzh"></div>
				<div class="bank_detail_common city_lbq_jslzh"></div>
				<div class="bank_detail_common city_lbq_wtzh"></div>
				<div class="bank_detail_common city_lbq_wglzh"></div>
				<div class="bank_detail_common city_lbq_xylzh"></div>
				<div class="bank_detail_common city_lbq_cydzh"></div>
				<div class="bank_detail_common city_lbq_gylzh"></div>
				<div class="bank_detail_common city_lbq_spzh"></div>
				<div class="bank_detail_common city_lbq_yhzh"></div>
				<div class="bank_detail_common city_lbq_hdzh"></div>
				<div class="bank_detail_common city_lbq_wzzh"></div>
				<div class="bank_detail_common city_lbq_bxzh"></div>
				<div class="bank_detail_common city_lbq_tczh"></div>
				<div class="bank_detail_common city_lbq_hyzh"></div>
				<div class="bank_detail_common city_lbq_fhzh"></div>
				<div class="bank_detail_common city_lbq_hjlzh"></div>
				<div class="bank_detail_common city_lbq_lzlzh"></div>
				<div class="bank_detail_common city_lbq_cndzh"></div>
				<div class="bank_detail_common city_gyq_lxzh"></div>
				<div class="bank_detail_common city_gyq_zgzzh"></div>
				<div class="bank_detail_common city_gyq_gyzh"></div>
				<div class="bank_detail_common city_frq_gysqzh"></div>
				<div class="bank_detail_common city_frq_frzh"></div>
				<div class="bank_detail_common city_fnq_fnhfsqzh"></div>
				<div class="bank_detail_common city_fnq_fnzh"></div>
				<div class="bank_detail_common city_ltkfq_ltzh"></div>
				<div class="bank_detail_common city_kpq_lnzh"></div>
				<div class="bank_detail_common city_kpq_mjgzh"></div>
				<div class="bank_detail_common city_kpq_xylzh"></div>
			</div>
			<!-- 右侧设备信息饼状图 -->
			<div class="map_pie">
				<div class="map_pie_2 map_pie_1" style="height:150px;">
					<div class="map_detail1"><span id="map_ts"></span>，终端设备<span class="active_word" id="mac_kind">0</span>种</div>
					<div class="map_detail"><span class="active_word">终端运行状态</span>：初始<span class="active_word" id="run_initial">0</span>台，投产<span class="active_word" id="run_complete">0</span>台，下线<span class="active_word" id="run_finish">0</span>台</div>
					<div class="map_detail"><span class="active_word">终端启用情况</span>：正常<span class="active_word" id="using_normal" style="color:green;">0</span>台，<span>异常<span class="active_word" id="using_error" style="color:red;">0</span>台</span>，未知<span class="active_word" id="using_unknown" style="color:#FFC000;">0</span>台&nbsp;</div>
				</div>
				<div class="map_pie_1" id="pie1" style="background:#fff;"></div>
				<div class="map_pie_1" id="pie2" style="background:#fff;"></div>
				<div class="map_pie_1" id="pie3" style="background:#fff;"></div>
			</div>
		</div>
	</div>
</div>
<script>
/*
	//全部故障替换闪烁图片
	$(function(){
		var img = $(".city_pool img[data-img]");
		console.log(img);
		$.each(img,function(i){
			var src = img[i].src;
			var inde = src.lastIndexOf(".")+1;
			img[i].src = src.substring(0,inde)+"gif";
		})
	})
*/
	//调整浏览器窗口变化之后网点地图的尺寸
	var mapPieHeight=$(".map_right").innerHeight();
	var mapDetailHeight=(mapPieHeight/4-30)/3;
	$(function(){
		if($(".map_right").innerHeight()>700){
	 		$(".map_pie").height(mapPieHeight);
	 		$(".city_common").height(mapPieHeight).css("background","#DFEAF0");
	 		$(".bank_detail_common").height(mapPieHeight).css("background","#DFEAF0");
	 		$(".map_pie_1").height(mapPieHeight/4);
	 		$(".map_pie_2").height(mapPieHeight/4-30);
	 		$(".map_detail1").height(mapDetailHeight).css("padding",7+"px"+" "+10+"px"+" "+0);
	 		$(".map_detail").height(mapDetailHeight).css("padding",0+" "+10+"px");
	 		$(".city_pool img").css("top",35+"px");
	 	}else{
	 		$(".map_pie").height(700);
	 		$(".city_common").height(700).css("background","#DFEAF0");
	 		$(".bank_detail_common").height(700).css("background","#DFEAF0");
	 		$(".map_pie_1").height(700/4);
	 		$(".map_pie_2").height(700/4-30);
	 		$(".map_detail1").height((700/4-30)/3).css("padding",7+"px"+" "+10+"px"+" "+0);
	 		$(".map_detail").height((700/4-30)/3).css("padding",0+" "+10+"px");
	 	}
		$(".map_right").scroll(function(event){
			//event.preventDefault();
			$(".city_common").css("background","#DFEAF0");	
			$(".bank_detail_common").css("background","#DFEAF0");		
		})
		topMenu(Tms_bankInfo);//顶部菜单功能;
	})
	$(window).resize(function(){
		if($(".map_right").innerHeight()>700){
	 		$(".map_pie").height(mapPieHeight);
	 		$(".city_common").height(mapPieHeight).css("background","#DFEAF0");
	 		$(".bank_detail_common").height(mapPieHeight).css("background","#DFEAF0");
	 		$(".map_pie_1").height(mapPieHeight/4);
	 		$(".map_pie_2").height(mapPieHeight/4-30);
	 		$(".map_detail1").height(mapDetailHeight).css("padding",0);
	 		$(".map_detail1").height(mapDetailHeight).css("padding",7+"px"+" "+10+"px"+" "+0);
	 		$(".map_detail").height(mapDetailHeight).css("padding",0+" "+10+"px");
	 		$(".city_pool img").css("top",35+"px");
	 	}else{
	 		$(".map_pie").height(700);
	 		$(".city_common").height(700).css("background","#DFEAF0");
	 		$(".bank_detail_common").height(700).css("background","#DFEAF0");
	 		$(".map_pie_1").height(700/4);
	 		$(".map_pie_2").height(700/4-30);
	 		$(".map_detail1").height((700/4-30)/3).css("padding",0);
	 		$(".map_detail1").height((700/4-30)/3).css("padding",7+"px"+" "+10+"px"+" "+0);
	 		$(".map_detail").height((700/4-30)/3).css("padding",0+" "+10+"px");
	 	}
	})
	/*选中效果  */
	$(".map_top_menu ul li").click(function(){
		//$(".city_zhs_zhflczh,.bank_detail_common").empty();
		//console.log($(".map_top_menu .nav li"));
		$(".map_top_menu .nav li").removeClass("activeBackgroundRgba");
		$(".form-controlIndex,.form-controlIndexEq,.form-controlIndexTh").removeClass("activeBackgroundRgba");
		if($(this).index() == "1"){
			$(".form-controlIndex").addClass("activeBackgroundRgba");
		}else if($(this).index() == "2"){
			$(".form-controlIndexEq").addClass("activeBackgroundRgba");
		}else if($(this).index() == "3"){
			$(".form-controlIndexTh").addClass("activeBackgroundRgba");
		}else{
			$(this).addClass("activeBackgroundRgba");	
		}
		
	});
	
/****************************************以下为地图区域故障代码******************************************/
	//启动定时器，每隔10秒，请求一次服务器，更新故障设备信息;
	$.ajax({
		url:"<%=basePath%>findUnitMachineStatus.do",
		type:"GET",
		cache:false,
		datatype:'json',
		beforeSend:function(){
			$(".city_pool .bank_detail_common").empty();
		},
		success:function(data){
			$("#mapTimeDate1").html(dateNew(data[0].dqTime));//最新更新时间
			Ec();
			searchRightDesc();
			tmsDescribe("唐山市");
			watchMapStatus(data);
		},
		error:function(){},
		complete:function(){
			mapStatusTimer = setInterval(function(){ 
				faultPolling();//主程序
				searchRightDesc();//右上角提示信息
				searchRightEc();//实时刷新右侧饼状图
				/*tmsDescribe("唐山市");*/ 
			},10000);
		}
	});
	//右侧饼状图随定时器刷新
	function searchRightEc(){
		if($(".map_top_menu .nav").find("li.activeBackgroundRgba").size()){
			Ec();
		}else{
			var select = $(".map_top_menu .nav li").find(".activeBackgroundRgba");
			if($(select).parent().hasClass("map_districtCounty")){
				if($(select).val() != "区/县"){
					var result = $(".map_top_menu li.map_districtCounty .form-control").find("option:selected").val();
					Ec1(result);
				}else{
					Ec();
				}
			}else if($(select).parent().hasClass("map_bank_branch")){
				if($(select).val() != "支行名称"){
					var result = $(select).find("option:selected").attr("data-code");
					Ec2(result);
				}else{
					var result = $(".map_top_menu li.map_districtCounty .form-control").find("option:selected").val();
					Ec1(result);
				}
			}else if($(select).parent().hasClass("map_machine_status")){
				var result = $(select).parent().prev().find("option:selected").attr("data-code");
				Ec2(result);
			}
		}
	}
	//右上角提示信息实时刷新
	function searchRightDesc(){
		if($(".map_top_menu .nav").find("li.activeBackgroundRgba").size()){
			mapDetail("","");
		}else{
			var select = $(".map_top_menu .nav li").find(".activeBackgroundRgba");
			if($(select).parent().hasClass("map_districtCounty")){
				if($(select).val() != "区/县"){
					var data = {districtCounty:$(select).val()};
					mapDetail(1,data);
				}else{
					mapDetail("","");
				}
			}else if($(select).parent().hasClass("map_bank_branch")){
				if($(select).val() != "支行名称"){
					var data = {unitCode:$(select).find("option:selected").attr("data-code")};
					mapDetail(2,data);
				}else{
					var data = {districtCounty:$(".map_top_menu li.map_districtCounty .form-control").val()};
					mapDetail(1,data);
				}
			}else if($(select).parent().hasClass("map_machine_status")){
				var data = {unitCode:$(select).parent().prev().find("option:selected").attr("data-code")};
				mapDetail(2,data);
			}else{
				mapDetail("","");
			}
		}
	}
	var upath;
	function faultPolling(){
		//alert(upath);
		var user;
		if(upath == "01"){
			 user = "<%=basePath%>findUnitMachineStatus01.do";
		}else if(upath == "02"){
			 user = "<%=basePath%>findUnitMachineStatus02.do";
		}else if(upath == "03"){
			 user = "<%=basePath%>findUnitMachineStatus03.do";
		}else{
			 user = "<%=basePath%>findUnitMachineStatus.do";
		}
		ajaxRequest(user);
	}
	function ajaxRequest(user){
		$.ajax({
			url:user,
			type:"GET",
			cache:false,
			datatype:'json',
			beforeSend:function(){
				$(".city_pool .bank_detail_common").empty();
			},
			success:function(data){
				$("#mapTimeDate1").html(dateNew(data[0].dqTime));//最新更新时间
				watchMapStatus(data);
			
			},
			error:function(){},
			complete:function(){}
		})
	}
	//设备故障状态维护
	function watchMapStatus(data){
		for(var i=0;i<data.length;i++){
			mapStatus(data,data[i].districtCounty,data[i].unitName,data[i].machineNo,data[i].machineName,data[i].pic,data[i].repStatus,data[i].repDesc);
		}
	}
	//故障状态维护公共方法
	function mapStatus(data,districtCounty,unitName,machineNo,machineName,pic,repStatus,repDesc){
		var dataImg = iteratorDataImg(districtCounty,unitName);//查找网点的自定义属性
		if(dataImg != ""){
			if(!$(".city_pool ."+dataImg).html()){//如果网点支行的内容为空
				if(repStatus == "1"){//如果设备状态为1
					modifyStatus(data,districtCounty,unitName,machineNo,"png",dataImg);
				}else{
					modifyStatus(data,districtCounty,unitName,machineNo,"gif",dataImg);
				}
				addMachineDetail(districtCounty,unitName,machineNo,machineName,pic,dataImg,repDesc,repStatus);
				if(repStatus == "1"){
					$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -60px");
				}else{
					$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -30px");
				}
			}else{
				//查找指定设备编号的Dom元素
				var resultArr = $(".city_pool ."+dataImg+" .device_common .device_detail span.code_right:contains("+machineNo+")");
				if($(resultArr).size()){//如果数组不为空
					if(repStatus == "1"){
						modifyStatus(data,districtCounty,unitName,machineNo,"png",dataImg);
					}else{
						modifyStatus(data,districtCounty,unitName,machineNo,"gif",dataImg);
					}	
				}else{
					if(repStatus == "1"){
						modifyStatus(data,districtCounty,unitName,machineNo,"png",dataImg);
					}else{
						modifyStatus(data,districtCounty,unitName,machineNo,"gif",dataImg);
					}
					addMachineDetail(districtCounty,unitName,machineNo,machineName,pic,dataImg,repDesc,repStatus);
					if(repStatus == "1"){
						$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -60px");
					}else{
						$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -30px");
					}
				}
			}
		}
	}
	//查找网点的自定义属性
	function iteratorDataImg(districtCounty,unitName){
		var result = "";
		$.each(Tms_bankInfo,function(j){
			if(Tms_bankInfo[j].city == districtCounty){
				$.each(Tms_bankInfo[j].bankList,function(k){
					if(Tms_bankInfo[j].bankList[k].title == unitName){
						result = Tms_bankInfo[j].bankList[k].dataImg;
						return "";
					}
				})
				return "";
			}
		})
		return result;
	}
	//添加设备详细信息
	function addMachineDetail(districtCounty,unitName,machineNo,machineName,pic,dataImg,repDesc,repStatus){
		if(pic){
			var innerText1 = "<div class=device_common><dl class=device_detail><dt><img src="+"../.."+pic+" alt=照片加载失败  /></dt><dd class=device_code><span class=code_left>设备编号：</span><span class=code_right>"+machineNo+"</span></dd>";
		}else{
			var innerText1 = "<div class=device_common><dl class=device_detail><dt><img src='"+"'alt=设备照片未上传  /></dt><dd class=device_code><span class=code_left>设备编号：</span><span class=code_right>"+machineNo+"</span></dd>";
		}
		if(repStatus == "2"){	
			var innerText2 = "<dd class=device_name><span class=name_left>设备名称：</span><span class=name_right>"+machineName+"</span></dd><dd class=device_unusual style=display:block;color:red; ><span class=unusual_left>故障原因：</span><span class=unusual_right>"+repDesc+"</span></dd></dl><div class=device_status></div></div>";
		}else{
			var innerText2 = "<dd class=device_name><span class=name_left>设备名称：</span><span class=name_right>"+machineName+"</span></dd><dd class=device_unusual><span class=unusual_left>机器异常：</span><span class=unusual_right></span></dd></dl><div class=device_status></div></div>";
		}
		innerText1 += innerText2;				
		$(".city_pool ."+dataImg).append(innerText1);
	}
	//修改设备网点地区故障状态
	function modifyStatus(data,districtCounty,unitName,machineNo,imgEnd,dataImg){
		var result = searchCountryAndBankStatus(data,districtCounty,unitName);//判断网点下的设备状态
		if(imgEnd == "png" && result == "1"){
			//$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -60px");
			modifyCountryStatus(districtCounty,imgEnd);//修改地区故障状态;
			modifyBankStatus(imgEnd,dataImg);//修改网点状态;
		}else{
			//$("."+dataImg+" .device_common span.code_right:contains("+machineNo+")").parent().parent().next().css("background-position","-190px -30px");
			modifyCountryStatus(districtCounty,imgEnd);//修改地区故障状态;
			modifyBankStatus(imgEnd,dataImg);//修改网点状态;
		}
	}
	//查询所有地区和网点下的设备状态只要有一个故障则返回false
	function searchCountryAndBankStatus(data,districtCounty,unitName){
		var statusNum = 1;
		$.each(data,function(i){
			if((data[i].districtCounty == districtCounty || data[i].unitName == unitName) && data[i].repStatus != 1){
				statusNum = 0;
				return ;
			}
		})
		return statusNum;
	}
	//修改网点状态
	function modifyBankStatus(imgEnd,dataImg){
		var src = $(".city_pool img[data-img="+dataImg+"]").attr("src");
		//console.log("网点状态替换old:"+src);
		var inde = src.lastIndexOf(".")+1;
		var newSrc = src.substring(0,inde)+imgEnd;
		//console.log("网点状态替换new:"+newSrc);
		$(".city_pool .city_detail img[data-img="+dataImg+"]").attr("src",newSrc);
	}
	//修改地区状态
	function modifyCountryStatus(districtCounty,imgEnd){
		$.each(Tms_bankInfo,function(i){
			if(Tms_bankInfo[i].city == districtCounty){
				//console.log(Tms_bankInfo[i].dataImg);
				var src = $(".city_pool .city_main img[data-img="+Tms_bankInfo[i].dataImg+"]").attr("src");
				var inde = src.lastIndexOf(".")+1;
				var newSrc = src.substring(0,inde)+imgEnd;
				$(".city_pool .city_main img[data-img="+Tms_bankInfo[i].dataImg+"]").attr("src",newSrc);
			}
		})	
	}
/****************************************以上为地图区域故障代码******************************************/
	window.onload = function(){
		tmsDescribe("唐山市");
	}
	//顶部地图菜单功能
	function topMenu(arr){
		$.each(arr,function(i){
			$(".map_top_menu .map_districtCounty select.form-control").append("<option value="+arr[i].city+" data-img="+arr[i].dataImg+" style=background:#fff;>"+arr[i].city+"</option>");
		})
	}
	$(".map_top_menu li:first-child").addClass("activeBackgroundRgba");//默认选中事件
	//唐山市单击事件
	$(".map_top_menu li:first-child").click(function(event){
		ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
		upath = null;
		Ec();
		searchRightDesc();//初始化右上角提示信息
		$(".map_top_menu li.map_machine_status").hide().children(".form-control").val("00");
		$(".mapTimeDate").show();
		$("#abnormalYi").hide();
		showCityMap(".city_pool","city_main");
		tmsDescribe("唐山市");
		$(this).next("li.map_districtCounty").children("select").val("区/县");
		$(this).next().next("li.map_bank_branch").children("select").val("支行名称");
		bankListMenu();//初始化支行名称菜单列表
	})
	//地图下拉列表功能
	$(".map_top_menu li.map_districtCounty .form-control").change(function(event){	
		upath = null;
		ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
		searchRightDesc();//初始化右上角提示信息
		$(".map_top_menu .nav li.map_machine_status").hide().children(".form-control").val("00");
		if($(event.target).val() != "区/县"){
			//$(this).find("option:selected").attr("data-img");
			findUnitName($(this).find("option:selected"));//请求服务器指定地区的下的网点支行名称列表
			showCityMap(".city_pool",$(this).find("option:selected").attr("data-img"));
			Ec1($(this).find("option:selected").val());
			countyDescribe($(this).find("option:selected").val());
		}else{
			showCityMap(".city_pool","city_main");
			tmsDescribe("唐山市");
			bankListMenu();//初始化支行名称列表菜单;
			Ec();
		}
	})
	//支行名称下拉列表菜单功能
	$(".map_top_menu li.map_bank_branch .form-control").change(function(event){
		$(".mapTimeDate").hide();
		searchRightDesc();//初始化右上角提示信息
		if(($(event.target).val() != "支行名称") && ($(this).find("option:selected").attr("data-code") != undefined)){
			var alt = $(this).find("option:selected").attr("data-img");
			var src = $(".city_pool img[data-img="+alt+"]").attr("src");
			src = src.substr(src.length-3,src.length);
	 		if(src == "png"){
	 			upath = "02";
	 			ajaxRequest("<%=basePath%>findUnitMachineStatus02.do");
	 		}else{
	 			ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
	 		}
			$(".map_top_menu .nav li.map_machine_status").show().children(".form-control").val("00");
			upath = null;
			//$(this).find("option:selected").attr("data-img");
			showBankDetail($(this).find("option:selected").attr("data-img"));
	    	Ec2($(this).find("option:selected").attr("data-code"));
	    	$("#map_ts").html("<span class='active_word'> "+$(event.target).val()+" </span>")
	    	var data = {unitCode:$(this).find("option:selected").attr("data-code")}
 			mapDetail(2,data);	
		}else{
			$(".map_top_menu .nav li.map_machine_status").hide().children(".form-control").val("00");
			var dataImg = $(".map_top_menu li.map_districtCounty .form-control").find("option:selected").attr("data-img");
			var value = $(".map_top_menu li.map_districtCounty .form-control").find("option:selected").val();
			showCityMap(".city_pool",dataImg);
			Ec1(value);
			countyDescribe(value);
		}
	})
	//设备状态查询条件
	$(".map_top_menu li.map_machine_status .form-control").change(function(){
		//01 查询全部  02 查询正常 03 查询异常
		/* findUnitMachineStatus01 查询全部
		findUnitMachineStatus02 查询正常
		findUnitMachineStatus03 查询异常 */
		//$(".city_zhs_zhflczh,.bank_detail_common").empty();
		/********当查询条件发生改变时立刻请求数据**********/
		var val = $(this).val();
		if(val == "01"){
			 ajaxRequest("<%=basePath%>findUnitMachineStatus01.do");
		}else if(val == "02"){
			 ajaxRequest("<%=basePath%>findUnitMachineStatus02.do");
		}else if(val == "03"){
			 ajaxRequest("<%=basePath%>findUnitMachineStatus03.do");
		}else{
			var alt = $(".map_top_menu li.map_bank_branch .form-control").find("option:selected").attr("data-img");
			var src = $(".city_pool img[data-img="+alt+"]").attr("src");
			src = src.subStr(src.length-3,src.length);
			if(src == "png"){
				ajaxRequest("<%=basePath%>findUnitMachineStatus02.do");
			}else{
				ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
			}
		}
		searchRightDesc();//初始化右上角提示信息
		upath = val;
	})
	//右下角唐山市描述消息
	function tmsDescribe(tms){
		Ec();
		var cityNum=0;
		for(var i=0;i<Tms_bankInfo.length;i++){
			for(var j=0;j<Tms_bankInfo[i].bankList.length;j++){
				cityNum++;
			}
		}
		InforMationLBT();
		$("#map_ts").html("<span class='active_word'>"+tms+"</span>共有网点<span class='active_word' id='city_num'>"+" </span>个");
		var data = "";
		mapDetail("",data);
	}
	//右下角地区描述信息
	function countyDescribe(unitName){
		var cityNum=0;
		for(var i=0;i<Tms_bankInfo.length;i++){
			for(var key in Tms_bankInfo[i]){
				if(Tms_bankInfo[i].city == unitName){
					cityNum=Tms_bankInfo[i].bankList.length;
				}
			}
		}
		$("#map_ts").html("<span class='active_word'> "+unitName+" </span>共有网点<span class='active_word' id='city_num'>"+cityNum+"</span>个");
		var data = {districtCounty:unitName}
		mapDetail(1,data);
	}
	//支行名称网点列表菜单初始化
	function bankListMenu(){
		$(".map_top_menu .map_bank_branch select.form-control").html("<option value=支行名称   data-code=   data-img=  style=background:#fff;>支行名称</option>");
	}
/**********************以上为地图顶部菜单功能代码**************************/	

	//左侧地区列表
	totalLeftMenu();
	function totalLeftMenu(){
		for(var i=0;i<Tms_bankInfo.length;i++){
			$(".total_map_menu").append("<li data-img="+Tms_bankInfo[i].dataImg+">"+Tms_bankInfo[i].city+"<i class='menu_ico'></i></li><ul class='map_left_menu2'></ul>");
			/* for(var j=0;j<Tms_bankInfo[i].bankList.length;j++){
				$(".total_map_menu>li").eq(i).next().append("<li data-img="+Tms_bankInfo[i].bankList[j].dataImg+">"+Tms_bankInfo[i].bankList[j].title+"</li>")
			} */
		}
	}
	//右侧唐山市区域地图单击事件
 	$(".city_main").on("click","area",function(event){
 		ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
 		$(".mapTimeDate").show();
 		$(".map_top_menu .nav li").removeClass("activeBackgroundRgba");
		$(".form-controlIndex").addClass("activeBackgroundRgba");//市级添加
		$(".form-controlIndexEq").removeClass("activeBackgroundRgba");//县级移除
		$(".map_right").scrollTop("0");//默认显示顶端
 		showCityMap(".city_pool",event.target.alt);
 		//showLeftList(".map_left",event.target.alt);	
 		$.each(Tms_bankInfo,function(i){//顶部菜单和地图单击联动功能
 			if(event.target.alt == Tms_bankInfo[i].dataImg){
 				$(".map_top_menu .map_districtCounty select.form-control").val(Tms_bankInfo[i].city);
 				findUnitName($(".map_top_menu .map_districtCounty select.form-control"));//请求支行名称下拉列表菜单
 				return ;
 			}
 		})
 		$.each(Tms_bankInfo,function(i,n){
 			if(n.dataImg==event.target.alt){
 					Ec1(n.city);
 			}
 		})
 		var cityName;
 		for(var i=0;i<Tms_bankInfo.length;i++){
 			for(var key in Tms_bankInfo[i]){
 				if(Tms_bankInfo[i].dataImg==event.target.alt){
 					cityName=Tms_bankInfo[i].city;
 				}
 			}
 		}
 		findUnitName($(".total_map_menu li[data-img='"+event.target.alt+"']"));
 		//var data = {districtCounty:$(".total_map_menu li[data-img='"+event.target.alt+"']").text()}
 		//mapDetail(1,data);
 		searchRightDesc();//初始化右上角提示信息
 	})
 	//地区详细图上银行网点点击事件;
 	$(".city_detail").on("click","area",function(event){
 		var alt = $(this).attr("alt");
		var src = $(".city_pool img[data-img="+alt+"]").attr("src");
		src = src.substr(src.length-3,src.length);
 		event.preventDefault();
 		if(src == "png"){
 			upath = "02";
 			ajaxRequest("<%=basePath%>findUnitMachineStatus02.do");
 		}else{
 			ajaxRequest("<%=basePath%>findUnitMachineStatus.do");
 		}
 		$(".map_top_menu li.map_machine_status").show().children(".form-control").val("00");
 		$(".mapTimeDate").hide();
 		$("#abnormalYi").show();
 		$(".map_top_menu .nav li").removeClass("activeBackgroundRgba");
		$(".form-controlIndexEq").addClass("activeBackgroundRgba");//县级添加
		$(".form-controlIndex").removeClass("activeBackgroundRgba");//市级移除
		$(".map_right").scrollTop("0");//默认显示顶端
		//console.log();
 		event.preventDefault();
 		showBankDetail(event.target.alt);//显示网点设备详细信息;
 		$.each(Tms_bankInfo,function(i){//支行名称下拉列表菜单和地图联动
 			$.each(Tms_bankInfo[i].bankList,function(j){
 				if(event.target.alt == Tms_bankInfo[i].bankList[j].dataImg){
 					$(".map_top_menu li.map_bank_branch select.form-control").val(Tms_bankInfo[i].bankList[j].title);
 					return ;
 				}
 			})
 			return ;
 		})
 		//$(".map_left_menu2"+" li[data-img="+event.target.alt+"]").addClass("active").siblings("li").removeClass("active");
 		$("#map_ts").html("<span class='active_word'> "+event.target.title+" </span>");
 		var result = $(".map_top_menu li.map_bank_branch .form-control").find("option:selected");
 		Ec2($(result).attr("data-code"));//获取支行的饼状图信息
 		searchRightDesc();//右侧提示信息刷新
 	})
	//显示区域地图
	function showCityMap(tmsMap,cityMap){
		$(tmsMap+' .'+cityMap).show().siblings(".city_common").hide();
		$(".city_pool .bank_detail_common").hide();
 	}
	//显示银行网点设备详细信息;
	function showBankDetail(attr){
		if(attr){
			$(".city_pool .city_common").hide();
			$(".city_pool .bank_detail_common").hide();
			$(".city_pool ."+attr).show();
		}
	}
//配置饼状图
	require.config({ 
	         paths:{  
	             'echarts' : '../../js/echarts/echarts', 
	             'echarts/chart/pie' : '../../js/echarts/echarts' 
	         } 
	     }); 
	// 使用 
	var cityNum=0;
		for(var i=0;i<Tms_bankInfo.length;i++){
			for(var j=0;j<Tms_bankInfo[i].bankList.length;j++){
				cityNum++;
			}
		}
	$("#map_ts").html("<span class='active_word'> "+$(".map_title").text()+" </span>共有网点<span class='active_word' id='city_num'> "+" </span>个")
	InforMationLBT();
	mapDetail("","");
	$(Ec());
	//初始化时获取饼图方法
	function Ec(){
	    require( 
	        [ 
	            'echarts', 
	            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载 
	        ], 
	        function (ec){
	                // 基于准备好的dom，初始化echarts图表 
	                var myChart = ec.init(document.getElementById("pie1"));
	                var myChart2 = ec.init(document.getElementById("pie2"));
	                var myChart3 = ec.init(document.getElementById("pie3"));
	                //第一个图表
	                $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineType.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:"",
	        	        beforeSend:function(xmlHttp){
	        	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    		            xmlHttp.setRequestHeader("Cache-Control","no-cache");
	        	        },
	        	        success:function(data){
	        	        	//console.log(data)
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端类型情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            },
	    	                            
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70']
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    })
	        	//按照机器的使用状态来统计数量 
	       		 $.ajax({
	    	        url:"<%=basePath%>queryDeployMachineStatus.do",
	    	        cache:false,
	    	        type:"post",
	    	        datatype:'json',
	    	        data:"",
	    	        beforeSend:function(xmlHttp){
	    	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    	            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	    	        },
	    	        success:function(data){
	    	        	//console.log(data)
	    	        	var username = [];
	    	        	for(var i=0;i<data.length;i++){
	    	        		username.push(data[i].name);
	    	        	}
	    	        	//console.log(username);
	    	        	option = { 
	    	                        title : { 
	    	                            text: '终端运行状态', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                                	
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	    	        	option.series[0].data =data;
	    	        	//option.legend[0].data = username;
	    	        		myChart2.setOption(option);
	    	        	 
	    	        },
	    	        complete:function(){
	    	        	
	    	        },
	    	        error:function(){
	    	        	
	    	        }
	    	    });
	       		
	       //按照机器的上报状态来统计数量 
	       		 $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineRepStatus.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:"",
	        	        beforeSend:function(){},
	        	        success:function(data){
	        	        	//console.log(data)
	        	        	//$("#informationLunBo_S").append("<li>唐山银行共有网点"++"个</li>")
	        	        	
	        	        	//console.log(data[0].value);
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端启用情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        }, */ 
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart3.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    });
	    	}
	     )
	};
	//单击区县时获取饼图方法
	function Ec1(city){
		var cityName=city;
	    require( 
	        [ 
	            'echarts', 
	            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载 
	        ], 
	        function (ec){
	    		{ 
	                // 基于准备好的dom，初始化echarts图表 
	                var myChart = ec.init(document.getElementById("pie1"));
	                var myChart2 = ec.init(document.getElementById("pie2"));
	                var myChart3 = ec.init(document.getElementById("pie3"));
	                //第一个图表
	                $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineType1.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:{districtCounty:cityName},
	        	        beforeSend:function(xmlHttp){
	        	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    		            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	        	        },
	        	        success:function(data){
	        	        	//console.log(data)
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端类型情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            },
	    	                            
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70']
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    })
	        	//按照机器的使用状态来统计数量 
	       		 $.ajax({
	    	        url:"<%=basePath%>queryDeployMachineStatus1.do",
	    	        cache:false,
	    	        type:"post",
	    	        datatype:'json',
	    	        data:{districtCounty:cityName},
	    	        beforeSend:function(xmlHttp){
	    	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    	            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	    	        },
	    	        success:function(data){
	    	        	var username = [];
	    	        	for(var i=0;i<data.length;i++){
	    	        		username.push(data[i].name);
	    	        	}
	    	        	//console.log(username);
	    	        	option = { 
	    	                        title : { 
	    	                            text: '终端运行状态', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                                	
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	    	        	option.series[0].data =data;
	    	        	//option.legend[0].data = username;
	    	        		myChart2.setOption(option);
	    	        	 
	    	        },
	    	        complete:function(){
	    	        	
	    	        },
	    	        error:function(){
	    	        	
	    	        }
	    	    });
	       		
	       //按照机器的上报状态来统计数量 
	       		 $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineRepStatus1.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:{districtCounty:cityName},
	        	        beforeSend:function(){},
	        	        success:function(data){
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端启用情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        }, */ 
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart3.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    });
	         }
	    	}
	     )
	}
	//单击支行名称获取饼图数据
	function Ec2(city){
		var cityName=city;
	    require( 
	        [ 
	            'echarts', 
	            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载 
	        ], 
	        function (ec){
	    		{ 
	                // 基于准备好的dom，初始化echarts图表 
	                var myChart = ec.init(document.getElementById("pie1"));
	                var myChart2 = ec.init(document.getElementById("pie2"));
	                var myChart3 = ec.init(document.getElementById("pie3"));
	                //第一个图表
	                $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineType2.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:{unitCode:cityName},
	        	        beforeSend:function(xmlHttp){
	        	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    		            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	        	        },
	        	        success:function(data){
	        	        	//console.log(data)
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端类型情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            },  
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70']
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    })
	        	//按照机器的使用状态来统计数量 
	       		 $.ajax({
	    	        url:"<%=basePath%>queryDeployMachineStatus2.do",
	    	        cache:false,
	    	        type:"post",
	    	        datatype:'json',
	    	        data:{unitCode:cityName},
	    	        beforeSend:function(xmlHttp){
	    	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
	    	            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	    	        },
	    	        success:function(data){
	    	        	var username = [];
	    	        	for(var i=0;i<data.length;i++){
	    	        		username.push(data[i].name);
	    	        	}
	    	        	//console.log(username);
	    	        	option = { 
	    	                        title : { 
	    	                            text: '终端运行状态', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                                	
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	    	        	option.series[0].data =data;
	    	        	//option.legend[0].data = username;
	    	        		myChart2.setOption(option);
	    	        	 
	    	        },
	    	        complete:function(){
	    	        	
	    	        },
	    	        error:function(){
	    	        	
	    	        }
	    	    });
	       		
	       //按照机器的上报状态来统计数量 
	       		 $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineRepStatus2.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:{unitCode:cityName},
	        	        beforeSend:function(){},
	        	        success:function(data){
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端启用情况', 
	    	                            subtext: '', 
	    	                            x:'center' 
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        }, */ 
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart3.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    });
	         }
	    	}
	     )
	}
	function mapDetail(i,data){
		//var unitCode=unitCode;
		//第一个图表
		var url1="<%=basePath%>queryDeployMachineType"+i+".do"
		//第二个图表
		var url2="<%=basePath%>queryDeployMachineStatus"+i+".do"
		//第三个图表
		var url3="<%=basePath%>queryDeployMachineRepStatus"+i+".do";
		//第一个图表
        $.ajax({
	        url:url1,
	        cache:false,
	        type:"post",
	        datatype:'json',
	        data:data,
	        beforeSend:function(){},
	        success:function(data){
	        	//console.log(data)
	        	$("#mac_kind").html(" "+data.length+" ")
	        },
	        complete:function(){},
	        error:function(){}
	    })
	//按照机器的使用状态来统计数量 
		 $.ajax({
	        url:url2,
	        cache:false,
	        type:"post",
	        datatype:'json',
	        data:data,
	        beforeSend:function(){},
	        success:function(data){
	        	//console.log(data)
	        	$("#run_initial,#run_complete,#run_finish").html(" "+0+" ");
	        	for(var key in data){
	        		if(data[key].name=="初始"){
	        			$("#run_initial").html(" "+data[key].value+" ");
	        		}if(data[key].name=="已投产"){
	        			$("#run_complete").html(" "+data[key].value+" ");
	        		}if(data[key].name=="已下线"){
	        			$("#run_finish").html(" "+data[key].value+" ");
	        		}
	        	}
       	 	},
	        complete:function(){},
	        error:function(){}
    });
//按照机器的上报状态来统计数量 
		 $.ajax({
	        url:url3,
	        cache:false,
	        type:"post",
	        datatype:'json',
	        data:data,
	        beforeSend:function(){},
	        success:function(data){
	        	//console.log(data)
	        	$("#using_normal,#using_error,#using_unknown").html(" "+0+" ");
	        	var use = [];
	        	for(var key in data){
	        		if(data[key].name=="正常"){
	        			$("#using_normal").html(" "+data[key].value+" ");
	        			use.push(data[key].value);
	        		}if(data[key].name=="异常"){
	        			$("#using_error").html(" "+data[key].value+" ");
	        			use.push(data[key].value);
	        		}if(data[key].name=="未知"){
	        			$("#using_unknown").html(" "+data[key].value+" ");
	        			use.push(data[key].value);
	        		}
	        	}
	        	// 
	        },
	        complete:function(){},
	        error:function(){}
	    });
	}
	function findUnitName(cityS){
		$.ajax({
			 url:"<%=basePath%>findUnitName.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{cityS:cityS.val()},
			 beforeSend:function(){
				 bankListMenu();//初始化网点支行名称菜单列表;
			 },
			 success:function(data){
				var county = $(".map_top_menu .map_districtCounty select.form-control").val();
				$.each(data,function(i){
					var dataImg = iteratorDataImg(county,data[i].unitName);//调用故障维护中的遍历支行名称自定义属性的方法
					$(".map_top_menu .map_bank_branch select.form-control").append("<option value="+data[i].unitName+" data-code="+data[i].unitCode+" data-img="+dataImg+" style=background:#fff;>"+data[i].unitName+"</option>");
				})
				var cityNum=0;
		    	for(var i=0;i<Tms_bankInfo.length;i++){
		    		for(var key in Tms_bankInfo[i]){
		    			if(Tms_bankInfo[i].city == county){
		    				cityNum = Tms_bankInfo[i].bankList.length;
		    			}
		    		}
		    	}	
		    	$("#map_ts").html("<span class='active_word'> "+county+" </span>共有网点<span class='active_word' id='city_num'> "+cityNum+" </span>个");
			 },
			 complete:function(){},
			 error:function(){}
		})
	}
 </script>

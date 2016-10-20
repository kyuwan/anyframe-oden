/*
 * Copyright 2010 SAMSUNG SDS Co., Ltd. All rights reserved.
 *
 * No part of this "source code" may be reproduced, stored in a retrieval
 * system, or transmitted, in any form or by any means, mechanical,
 * electronic, photocopying, recording, or otherwise, without prior written
 * permission of SAMSUNG SDS Co., Ltd., with the following exceptions:
 * Any person is hereby authorized to store "source code" on a single
 * computer for personal use only and to print copies of "source code"
 * for personal use provided that the "source code" contains SAMSUNG SDS's
 * copyright notice.
 *
 * No licenses, express or implied, are granted with respect to any of
 * the technology described in this "source code". SAMSUNG SDS retains all
 * intellectual property rights associated with the technology described
 * in this "source code".
 *
 */
package org.anyframe.oden.admin.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.anyframe.oden.admin.domain.Log;
import org.anyframe.oden.admin.service.HistoryService;
import org.anyframe.oden.admin.service.JobService;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controller class for exceldownload.
 * @author Hong JungHwan
 */
@Controller
@RequestMapping("/excel.do")
public class ExcelDownloadController  {
	@Resource
    private HistoryService historyService = null;
	@Resource
	private JobService jobService = null;
	
	@RequestMapping(params="method=history")
	public void export2excelHistory(@RequestParam("cmd") String cmd, 
			Model model, HttpServletResponse res) throws Exception {
		List<Log> logs = historyService.findByPkExcel(cmd);
		
		res.reset();
		res.setHeader("Content-type","application/xls");
		res.setHeader("Content-disposition","inline; filename=history.xls");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		
		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue(new HSSFRichTextString("ID"));
		row0.createCell(1).setCellValue(new HSSFRichTextString("JOB NAME"));
		row0.createCell(2).setCellValue(new HSSFRichTextString("DATE"));
		row0.createCell(3).setCellValue(new HSSFRichTextString("COUNTS(SUCCESS/TOTAL)"));
		row0.createCell(4).setCellValue(new HSSFRichTextString("USER ID"));
		
		int irow = 1;
		for(Log p : logs) {
			HSSFRow row = sheet.createRow(irow++);
			
			int icell = 0;
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getTxid()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getJob()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getDate()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getCounts()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getUser()));
		}
	    wb.write(res.getOutputStream());
	}
	
	@RequestMapping(params="method=historydetail")
	public void export2excelHistoryDetail(@RequestParam("txid") String txid,
			@RequestParam("cmd") String cmd, Model model,
			HttpServletResponse res) throws Exception {
		List<Log> logs = historyService.showExcel(txid , cmd);

		res.reset();
		res.setHeader("Content-type", "application/xls");
		res.setHeader("Content-disposition", "inline; filename=historydetail.xls");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue(new HSSFRichTextString("NO"));
		row0.createCell(1).setCellValue(new HSSFRichTextString("STATUS"));
		row0.createCell(2).setCellValue(new HSSFRichTextString("TARGET"));
		row0.createCell(3).setCellValue(new HSSFRichTextString("FILE"));
		row0.createCell(4).setCellValue(new HSSFRichTextString("MODE"));
		row0.createCell(5)
				.setCellValue(new HSSFRichTextString("Error Message"));

		int irow = 1;
		for (Log p : logs) {
			HSSFRow row = sheet.createRow(irow++);

			int icell = 0;
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getNo()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getSuccess()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getJob()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getPath()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getMode()));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString(p.getErrorlog()));
		}
		wb.write(res.getOutputStream());
	}
	
	@RequestMapping(params="method=compare")
	public void export2excelCompare(@RequestParam("cmd") String param,
			@RequestParam("tgt") String tgt, Model model,
			HttpServletResponse res) throws Exception {
		String[] tgts = tgt.split(",");
		List<HashMap> compares = jobService.excel(param);
		int icol = 2;
		
		res.reset();
		res.setHeader("Content-type", "application/xls");
		res.setHeader("Content-disposition", "inline; filename=comparetarget.xls");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue(new HSSFRichTextString("STATUS"));
		row0.createCell(1).setCellValue(new HSSFRichTextString("FILE"));
		for(String t : tgts) {
			row0.createCell(icol).setCellValue(new HSSFRichTextString(t));
			icol++;
		}
		
		int irow = 1;
		for (HashMap hm : compares) {
			HSSFRow row = sheet.createRow(irow++);

			int icell = 0;
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString((String)hm.get("status")));
			row.createCell(icell++).setCellValue(
					new HSSFRichTextString((String)hm.get("file")));
			for(String t : tgts) {
				row.createCell(icell++).setCellValue(
						new HSSFRichTextString((String)hm.get(t)));
			}
		}
		wb.write(res.getOutputStream());
	}
}
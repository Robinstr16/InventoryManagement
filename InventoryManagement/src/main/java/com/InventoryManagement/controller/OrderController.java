package com.InventoryManagement.controller;


import com.InventoryManagement.Dao.ProductDao;
import com.InventoryManagement.model.Orders;
import com.InventoryManagement.model.Products;
import com.InventoryManagement.security.CustomUserDetails;
import com.InventoryManagement.service.OrderService;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("userDetails")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/Order")
    public String viewOrder(ModelMap  modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<Orders> allOrders = orderService.getAllOrders();
        List<ProductDao> allProducts = orderService.getAllProducts();
        modelMap.addAttribute("allOrders",allOrders);
        modelMap.addAttribute("allProducts",allProducts);
        return "orderPage";
    }

    @PostMapping("/Order")
    public String addOrder(@RequestParam String receiverName,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date orderDate,
                           @RequestParam String receiverAddress,
                           @RequestParam String productName,
                           @RequestParam Long orderUnits,
                           @RequestParam String paymentStatus,
                           ModelMap modelMap) {

        List<ProductDao> allProducts = orderService.getAllProducts();
        modelMap.addAttribute("allProducts",allProducts);
        CustomUserDetails userDetails = (CustomUserDetails) modelMap.get("userDetails");
        orderService.saveOrder(receiverName,userDetails.getName(),orderDate,receiverAddress,productName,orderUnits,paymentStatus);
        List<Orders> allOrders = orderService.getAllOrders();
        modelMap.addAttribute("allOrders",allOrders);
        return "orderPage";
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestParam Long orderId,ModelMap modelMap){
        orderService.deleteOrder(orderId);
        List<Orders> allOrders = orderService.getAllOrders();
        modelMap.addAttribute("allOrders",allOrders);
        return "orderPage";
    }

    @GetMapping("/viewPdf")
    public String viewPdf() throws FileNotFoundException {
        String path="C:\\Users\\4W Tech\\Desktop\\PdfFiles\\bill.pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        //document.add(new Paragraph("Hello Coding"));
        float threeCol=190f;
        float twoCol=285f;
        float twoCol150=twoCol+150f;
        float twoColumnWidth[]={twoCol150,twoCol};
        float[] fullWidth={threeCol*3};
        Paragraph oneSpace=new Paragraph("\n");
        Table table=new Table(twoColumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable=new Table(new float[]{twoCol/2,twoCol/2});
        nestedTable.addCell(getHeadText("Invoice no"));
        nestedTable.addCell(getHeadTextValue("34567878"));
        nestedTable.addCell(getHeadText("Invoice Date"));
        nestedTable.addCell(getHeadTextValue("15/03/2001"));
        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
        Border gb=new SolidBorder(Color.BLUE,2f);
        Table divider=new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(oneSpace);
        document.add(divider);
        document.add(oneSpace);
        Table twoColTable=new Table(twoColumnWidth);
        twoColTable.addCell(getBillingAndShipping("Billing Information"));
        twoColTable.addCell(getBillingAndShipping("Shipping Information"));
        document.add(twoColTable.setMarginBottom(12f));
        Table twoColTable2=new Table(twoColumnWidth);
        twoColTable2.addCell(getCell("Company",true));
        twoColTable2.addCell(getCell("Name",true));
        twoColTable2.addCell(getCell("Coding Error",false));
        twoColTable2.addCell(getCell("Coding",false));
        document.add(twoColTable2);
        Table twoColTable3=new Table(twoColumnWidth);
        twoColTable3.addCell(getCell("Name",true));
        twoColTable3.addCell(getCell("Address",true));
        twoColTable3.addCell(getCell("R Govalu",false));
        twoColTable3.addCell(getCell("8504 Gulseth Terra,Kmdfkfdl kd\njrkf fdnmgd",false));
        document.add(twoColTable3);
        float[] oneColumnWidth={twoCol150};
        Table oneColTable1=new Table(oneColumnWidth);
        oneColTable1.addCell(getCell("Address",true));
        oneColTable1.addCell(getCell("8504 Gulseth Terra,Kmdfkfdl kd\njrkf fdnmgd",false));
        oneColTable1.addCell(getCell("Email",true));
        oneColTable1.addCell(getCell("stern@example.com",false));
        document.add(oneColTable1.setMarginBottom(10f));
        Table tableDivider2=new Table(fullWidth);
        Border dgb=new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));
        document.close();
        return "orderPage";
    }

    static Cell getHeadText(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setBold();
    }

    static Cell getHeadTextValue(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER);
    }

    static Cell getBillingAndShipping(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setBold();
    }
    static Cell getCell(String textValue,boolean isBold){
        Cell myCell=new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold?myCell.setBold():myCell;
    }

}

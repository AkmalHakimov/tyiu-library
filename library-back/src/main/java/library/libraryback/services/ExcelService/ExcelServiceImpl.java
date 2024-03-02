package library.libraryback.services.ExcelService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.entity.Book;
import library.libraryback.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService{

    private final BookRepo bookRepo;

    @Override
    public HttpEntity<?> downloadBooksExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Adabiyotlar.xls";

        response.setHeader(headerKey,headerValue);

        List<Book> allBooks = bookRepo.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Books Info");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("#");
        row.createCell(1).setCellValue("Ismi");
        row.createCell(2).setCellValue("Muallifi");
        row.createCell(3).setCellValue("Yo'nalishi");
        row.createCell(4).setCellValue("Nashriyot");
        row.createCell(5).setCellValue("Sanasi");

        int dataRowIndex = 1;

        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-mm-yyyy"));

        for (Book book : allBooks) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(dataRowIndex);
            dataRow.createCell(1).setCellValue(book.getName());
            dataRow.createCell(2).setCellValue(book.getAuthor());
            dataRow.createCell(3).setCellValue(book.getCategory().getName());
            dataRow.createCell(4).setCellValue(book.getPublisher());
            Cell cell = dataRow.createCell(5);
            cell.setCellValue(book.getBook_date());
            cell.setCellStyle(cellStyle);
            dataRowIndex++;
        }

        sheet.setColumnWidth(1, 9000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(5, 7000);

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

        return ResponseEntity.ok("");
    }
}

import axios from "axios";

export default ()=>{
  let access_token = localStorage.getItem("access_token");
  let tokenHeader = access_token ? { Authorization: `Bearer ${access_token}` } : {};
    axios({
        method: 'get',
        url: 'http://localhost:8080/api/book/excel/download',
        responseType: 'blob',
        headers: {
          'Content-Type': 'application/octet-stream',
          Authorization: `Bearer ${access_token}`
        }
      })
      .then(response => {
        // Create a URL for the blob
        const url = URL.createObjectURL(response.data);
    
        // Create a temporary <a> element and set its attributes
        const link = document.createElement('a');
        link.href = url;
        link.download = 'Adabiyotlar.xls';
    
        // Append the <a> element to the document body
        document.body.appendChild(link);
    
        // Programmatically click the link to start the download
        link.click();
    
        // Clean up the temporary <a> element and URL object
        document.body.removeChild(link);
        URL.revokeObjectURL(url);
      })
      .catch(error => {
        console.error('Error downloading Excel file:', error);
      });
}
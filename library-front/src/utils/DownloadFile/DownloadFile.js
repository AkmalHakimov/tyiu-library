import axios from "axios";
import { ErrorNotify } from "../ErrorNotify/ErrorNotify";
import { BASE_URL } from "../../configure/ApiRequestor/ApiRequest";

export default (item) => {
    // Define an async function
    const downloadFile = async (item) => {
      const downloadUrl = BASE_URL + `api/file/download?id=${item.pdfId}`;
  
      try {
        let access_token = localStorage.getItem("access_token");
        let tokenHeader = access_token ? { Authorization: `Bearer ${access_token}` } : {};
        const response = await axios.post(downloadUrl, null, {
          responseType: 'blob', // Set the response type to blob
          headers: tokenHeader, 
        });
        
  
        if (response.data instanceof Blob) {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const a = document.createElement('a');
          a.href = url;
          a.download = 'downloadedFile.pdf'; // Provide a default filename
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          window.URL.revokeObjectURL(url);
        } else {
          ErrorNotify("Login First")
          // Handle error, e.g., show an error message to the user
        }
      } catch (error) {
        ErrorNotify("Login First")
        // Handle error, e.g., show an error message to the user
      }
    };
  
    // Call the downloadFile function
    downloadFile(item);
  };
  
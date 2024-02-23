import axios from "axios";

export default (item) => {
    // Define an async function
    const downloadFile = async (item) => {
      const downloadUrl = `http://localhost:8080/api/file/download?id=${item.pdfId}`;
  
      try {
        const response = await axios.get(downloadUrl, {
          responseType: 'blob', // Set the response type to blob
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
          console.error('Invalid response data:', response.data);
          // Handle error, e.g., show an error message to the user
        }
      } catch (error) {
        console.error('Error downloading file:', error);
        // Handle error, e.g., show an error message to the user
      }
    };
  
    // Call the downloadFile function
    downloadFile(item);
  };
  
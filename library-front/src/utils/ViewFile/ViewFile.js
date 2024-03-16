import { BASE_URL } from "../../configure/ApiRequestor/ApiRequest";

export default (item) => {
    try {
      const newTab = window.open(BASE_URL + `api/file?id=${item.pdfId}`, '_blank');
  
      if (newTab) {
        newTab.focus();
      } else {
        console.error('Unable to open a new tab. Please check your browser settings.');
      }
    } catch (error) {
      console.error('Error opening new tab:', error);
    } 
  };
    
import { select, takeEvery, put, call } from "redux-saga/effects";
import ApiRequest from "../../../../configure/ApiRequestor/ApiRequest";
import { BooksActions } from "../Reducers/BooksReducer";
import { message } from "antd";

function* getBooks() {
  const states = yield select((state) => state.Books);
  yield put(BooksActions.setLoading());
  const res = yield ApiRequest(`/book`, "get", null, {
    page: states.searchInp ? 1 : states.page,
    categoryId: states.selectVal,
    search: states.searchInp,
    offset: states.pageSize,
  });
  yield put(BooksActions.setTotalPages(res.data.totalElements));
  yield put(BooksActions.setData(res.data.content));
  yield put(BooksActions.setLoading());
}

function* handleSave(action) {
  const states = yield select((state) => state.Books);
  const value = action.payload.value;
  // const form = action.payload.form;
  let obj = {
    name: value.name,
    author: value.author,
    description: value.description,
    publisher: value.publisher,
    bookDate: value.bookDate,
    qrCodeId: value.qrCodeId,
    pdfId: states.hasFile ? states.hasFile : states.currentItm.pdfId,
    categoryId: value.categoryId?.value ?? value.categoryId,
  };
  if (states.currentItm) {
    yield ApiRequest(`/book?bookId=${states.currentItm.id}`, "put", obj);
    yield put(BooksActions.setCurrentItm(""));
  } else {
    yield ApiRequest("/book", "post", obj);
  }
  yield call(getBooks);
  yield put(BooksActions.setModalVisible());
  // form.resetFields();
}

function* delItem(action) {
  yield ApiRequest("/book/" + action.payload, "delete");
  yield call(getBooks);
}

function* editItm(action) {
  const states = yield select((state) => state.category);
  yield put(BooksActions.setModalVisible());
  const form = action.payload.form;
  const item = action.payload.item;
  form.setFieldValue("categoryId", {
    label: item.categoryName,
    value: item.categoryId,
  });
  form.setFieldValue("name", item.name);
  form.setFieldValue("author", item.author);
  form.setFieldValue("publisher", item.publisher);
  form.setFieldValue("bookDate", item.bookDate);
  form.setFieldValue("description", item.description);
  form.setFieldValue("qrCodeId", item.qrCodeId);
  states.currentItm = item;
}

function* handleFile(action){
  const info = JSON.parse(action.payload)
  let formData = new FormData();
    formData.append("file", info.file);
    // formData.append("prefix", "/kitoblar/pdfs");
    formData.append("prefix", "/kitoblar/pdfs_temp");
     const res = yield ApiRequest(
      "/file",
      "post",
      formData,
    )
    BooksActions.setHasFile(res.data);
    BooksActions.setSavedFile(JSON.stringify(info.file));
    message.success(`${info.file.name} file uploaded successfully`);
}

function* getCategoryBook(action){
  if(action.payload){
    const res = yield ApiRequest(`/book?categoryId=${action.payload}`,"get")
    yield put(BooksActions.setData(res.data.content))
  }
 
}

export function* BooksSaga() {
  yield takeEvery("Books/getBooks", getBooks);
  yield takeEvery("Books/handleSave", handleSave);
  yield takeEvery("Books/delItem", delItem);
  yield takeEvery("Books/editItm", editItm);
  yield takeEvery("Books/handleFile", handleFile);
  yield takeEvery("Books/getCategoryBook", getCategoryBook);
}

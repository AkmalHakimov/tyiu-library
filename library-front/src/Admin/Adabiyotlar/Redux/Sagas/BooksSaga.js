import { select, takeEvery, put, call } from "redux-saga/effects";
import ApiRequest from "../../../../configure/ApiRequestor/ApiRequest";
import { BooksActions } from "../Reducers/BooksReducer";

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
}

function* delItem(action) {
  yield ApiRequest("/book/" + action.payload, "delete");
  yield call(getBooks);
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
  yield takeEvery("Books/getCategoryBook", getCategoryBook);
}

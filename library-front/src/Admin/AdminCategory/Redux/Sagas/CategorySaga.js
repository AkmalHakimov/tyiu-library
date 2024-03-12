import { select, takeEvery, put, call } from "redux-saga/effects";
import ApiRequest from "../../../../configure/ApiRequestor/ApiRequest";
import { categoryActions } from "../Reducers/CategoryReducer";
import { BooksActions } from "../../../Adabiyotlar/Redux/Reducers/BooksReducer";

function* getCategories() {
  const states = yield select((state) => state.category);
  yield put(categoryActions.setIsLoading());

  const res = yield ApiRequest(
    `/category?page=${states.searchInp ? 1 : states.page}&search=` +
      states.searchInp,
    "get"
  );
  yield put(categoryActions.setIsLoading());
  yield put(categoryActions.setDataSource(res.data.content));
  yield put(categoryActions.setTotalPages(res.data.totalElements));
}

function* getAllCategories() {
  const res = yield ApiRequest("/category/all", "get");
  const newOptions = [
    { label: "Yo'nalishlar", value: "" },
    ...(res.data?.map((item) => ({
      label: item.name,
      value: item.id,
      key: item.id,
    })) || []),
  ];
  yield put(BooksActions.setOptions(newOptions));
}

function* HandleSave() {
  const states = yield select((state) => state.category);
  if (states.currentItm) {
    yield ApiRequest("/category/" + states.currentItm.id, "put", {
      name: states.categoryInp,
    });
    yield put(categoryActions.setCurrentItm(""));
  } else {
    yield ApiRequest("/category", "post", {
      name: states.categoryInp,
    });
  }
  yield call(getCategories);
  yield put(categoryActions.setCategoryInp(""));
  yield put(categoryActions.setIsModal());
}

function* handleDel(action) {
  yield ApiRequest(`/category/${action.payload}`, "delete");
  yield call(getCategories);
}

function* handleEdit(action) {
  yield put(categoryActions.setIsModal());
  yield put(categoryActions.setCategoryInp(action.payload.name));
  yield put(categoryActions.setCurrentItm(action.payload));
}

export function* categorySaga() {
  yield takeEvery("category/getCategories", getCategories);
  yield takeEvery("category/handleSave", HandleSave);
  yield takeEvery("category/handleDel", handleDel);   
  yield takeEvery("category/handleEdit", handleEdit);
  yield takeEvery("category/getAllCategories", getAllCategories);
}

import React, {useEffect, useState} from 'react'
import { Form,DatePicker, Select,Table,Button, Input,Modal } from 'antd';
import ApiRequest from '../../../utils/ApiRequest';

export default function TableAdabiyotlar() {

    const [data,setData] = useState([])
    const [totalPages,setTotalPages] = useState("");
    const [loading,setLoading] = useState(false)
    const [searchInp,setSearchInp] = useState("")
    const [selectVal,setSelectVal] = useState(0)
    const [page,setPage] = useState(1)
    const [modalVisible,setModalVisible] = useState(false)
    const [currentItm,setCurrentItm] = useState("")
  const [form] = Form.useForm();


    const [options,setOptions] = useState([
      {label: "Yo'nalishlar", value: ""}
    ])

    const columns =[
        {
            title: '#',
            dataIndex: 'id',
            sorter: true,
            render: (name) =>`${name}`,
            width: '10%',
          },
          {
            title: "Kitob nomi",
            dataIndex: "name",
            with: "30%"
          },
          {
            title: "Kitob muallifi",
            dataIndex: "author",
            with: "30%"
          },
          {
            title: "Kitob sanasi",
            dataIndex: "bookDate",
            render: (bookDate) => new Date(bookDate).getFullYear(),
            with: "30%"
          },
          {
            title: "Delete",
            dataIndex: "",
            render: (item) => <Button type='primary' onClick={()=>delItem(item.id)}>Delete</Button>,
            with: "30%"
          },
          {
            title: "Edit",
            dataIndex: "",
            render: (item) => <Button type='primary' onClick={()=>editItm(item)}>Edit</Button>,
            with: "30%"
          }
    ]

    useEffect(()=>{
        getBooks(page)  
        getCategories()
    },[])

    function delItem(bookId){
        ApiRequest("/book/" + bookId,"delete").then(res=>{
          getBooks(page)
        })
    }

    function editItm(item){
      console.log(item);
       setModalVisible(true)
       form.setFieldValue("name",item.name)
       form.setFieldValue("publisher",item.publisher)
       form.setFieldValue("author",item.author)
       form.setFieldValue("bookDate",item.bookDate)
       form.setFieldValue("description",item.description)
       form.setFieldValue("categoryId",item.categoryId)
       setCurrentItm(item)
    }

    function getCategories(){
      ApiRequest("/category/all", "get").then(res=>{
        res.data.map((item,index)=>{
            options.push({
              label: item.name,
              value:item.id,
              key: index  
            })
            setOptions([...options])
        })
      })
    }
    function getBooks(page,search = searchInp,select = selectVal){
      setLoading(true)
        ApiRequest(`/book?page=${page}&categoryId=${select}&search=` + (search),"get").then(res=>{
            setTotalPages(res.data.totalElements)
            setData(res.data.content)
            setLoading(false)
        })
    }

    function openModal(){
      setModalVisible(true)
    }
  function onFinish(value){
    console.log(value);
      console.log(value);
      ApiRequest("/book","post",value).then(res=>{
          getBooks(page)
      })
      setModalVisible(false)
      form.resetFields()
  }

  return (
    <div>
        <div className='mb-4 d-flex align-items-center justify-content-between'>
        <div style={{width:450}} className='d-flex align-items-center justify-content-between'>
        <Button onClick={openModal} type='primary'>Add a row</Button>
        <Select
          defaultValue="Yo'nalishlar"
          onChange={(val)=>{setSelectVal(val); getBooks(page,searchInp,val)}}
          style={{
            width: 300,
          }}
          options={options}
        />
        </div>
        <div><Input value={searchInp} onChange={(e)=>{setSearchInp(e.target.value); getBooks(page, e.target.value)}} placeholder='search...'/></div>
        </div>
        <Table pagination={{
          pageSize: 6,
          total:totalPages,
          onChange: (page)=>{
            getBooks(page)
          },
        }}   loading={loading} columns={columns} dataSource={data}/>
         <Modal
                 title="Adabiyot qo'shish"
                 width={1000}
                 centered
                 open={modalVisible}
                 onCancel={()=>{setModalVisible(false); form.resetFields()}}
                 footer={[
                     <Button key="cancel" onClick={() => {setModalVisible(false); form.resetFields()}}>
                         Cancel
                     </Button>,
                     <Button
                         key="submit"
                         form="adabiyotForm"  // ID of the form
                         htmlType="submit"
                         type="primary"
                     >
                         Submit
                     </Button>,
                 ]}
      >
        <div>
<Form
      form={form}
      layout="vertical"
      id='adabiyotForm'
      onFinish={onFinish}
    //   requiredMark={requiredMark === 'customize' ? customizeRequiredMark : requiredMark}
    >
            <Form.Item name="categoryId" rules={[{required: true, message:"Yo'nalish tanlash majburiy"}]} required label="Yo'nalish tanlang" hasFeedback>
            <Select
          defaultValue="Yo'nalishlar"
          options={options}
        />
    </Form.Item>
      <Form.Item label="Kitob Nomi" rules={[{required: true, message:"Kitob ismini tanlash majburiy"}]} required name="name">
        <Input placeholder="text" />
      </Form.Item>
      <Form.Item
      required
      rules={[{required: true, message:"Muallif tanlash majburiy"}]}
        name="author"
        label="Muallif"
      >
        <Input placeholder="text" />
      </Form.Item>

      <Form.Item rules={[{required: true, message:"Nashriyot tanlash majburiy"}]} required label="Nashriyot" name="publisher">
        <Input placeholder="text" />
      </Form.Item>
      <Form.Item rules={[{required: true, message:"Sana tanlash majburiy"}]} required label="Sanasi" name="bookDate">
      <DatePicker
      style={{
        width: "100%"
      }}
      />
      </Form.Item>

      <Form.Item label="Izoh" name="description">
        <Input.TextArea/>
      </Form.Item>
    </Form>
    </div>
      </Modal>
    </div>
  )
}

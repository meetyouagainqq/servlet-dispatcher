function deleteFruit(id) {
   if (confirm("是否确认删除?")){
       window.location.href='delete.do?fid='+id;
   }
}
// function page(pageNo) {
//      window.location.href='index?pageNo='+pageNo;
// }
function page(pageNo){
    window.location.href="index?pageNo="+pageNo;
}
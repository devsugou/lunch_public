/**
 * 削除ボタンがクリックされたときの処理
 */
document.addEventListener("DOMContentLoaded", function(){
    let deleteButtons = document.querySelectorAll(".deleteBtn");

    deleteButtons.forEach(function(button){
        button.addEventListener("click", function() {
            let storeId = this.getAttribute("data-storeid");
            let storeName = this.getAttribute("data-storename");
            //Set the store name in the modal
            document.querySelector("#deleteModal .modal-body").innerHTML =  storeName + "を本当に削除しますか？";
            //Set the storeId in the modal's hidden input
            document.querySelector("#deleteModal #storeId").value = storeId;
            document.querySelector("#deleteModal #storeName").value = storeName;
        });
    });
});

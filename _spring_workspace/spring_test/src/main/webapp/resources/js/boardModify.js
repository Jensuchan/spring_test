console.log("board modify js in");

async function removeFileFromServer(uuid) {
    try {
        const url = "/board/file/" + uuid;
        const config = {
            method: "delete"
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
};


document.addEventListener('click', (e) => {
    console.log(e.target);
    if (e.target.classList.contains('file-x')) {
        console.log('x버튼 찾음');
        let uuid = e.target.dataset.uuid;
        console.log("uuid >> ", uuid);
        let li = e.target.closest('li');
        removeFileFromServer(uuid).then(result => {
            if (result === '1') {
                alert('파일 삭제 성공!!');
                li.remove();
                location.reload();
            } else {
                alert("파일 삭제 실패!!")
            }
        })
    }
})
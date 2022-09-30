const routeId = document.getElementById('routeId').value;
const commentCtnr = document.getElementById('commentCtnr');

const csrHeaderName = document.head.querySelector('[name=_csrf_header]').content;
const csrHeaderValue = document.head.querySelector('[name=_csrf]').content;

const commentForm = document.getElementById('commentForm');
commentForm.addEventListener("submit", handleCommentSubmit);

const allComments = [];

const displayComment = (comments) => {
    commentCtnr.innerHTML = comments.map(
        (c) => {
            return asComment(c);
        }
    ).join('');
}

function asComment(c) {
    let commentHtml = `<div id="commentCtnr-${c.commentId}">`

    commentHtml += `<h4>${c.user} (${c.created})</h4><br/>`
    commentHtml += `<p>${c.message}</p>`
    commentHtml += `</div>`

    return commentHtml;
}

fetch(`http://localhost:8080/api/${routeId}/comments`)
    .then(response => response.json())
    .then(data => {
        for (const comment of data) {
            allComments.push(comment);
        }
        displayComment(allComments);
    });


async function handleCommentSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;
    const formData = new FormData(form);

    try {
        const responseData = await postFormDataAsJson({url, formData});

        commentCtnr.insertAdjacentHTML("afterbegin", asComment(responseData))
        form.reset();
    } catch (error) {
        console.log(error);
    }
}

async function postFormDataAsJson({url, formData}){

    const plainFormData = Object.fromEntries(formData.entries());
    const formDataAsJsonString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
            [csrHeaderName] : csrHeaderValue,
            "Content-Type" : "application/json",
            "Accept" : "application/json"
        },
        body: formDataAsJsonString
    }

    const response = await fetch(url, fetchOptions);

    if ((!response.ok)){
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}
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

}
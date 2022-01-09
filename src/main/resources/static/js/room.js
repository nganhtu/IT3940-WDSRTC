const socket = io("https://wdsrtc.herokuapp.com")

const myPeer = new Peer(undefined, {}) // use default server (PeerServer Cloud Service)

const videoGrid = document.getElementById('video-grid')
const myVideo = document.createElement('video')
myVideo.muted = true    // not listen to my own voice
const peers = []

navigator.mediaDevices.getUserMedia({
    video: true,
    audio: true
}).then(stream => {

    addVideoStream(myVideo, stream)

    myPeer.on('call', call => {
            call.answer(stream)
            const video = document.createElement('video')
            call.on('stream', userVideoStream => {
                addVideoStream(video, userVideoStream)
            })
        })

    socket.on('user-connected', userId => {
        // https://github.com/WebDevSimplified/Zoom-Clone-With-WebRTC/issues/52
        setTimeout(() => {
            connectToNewUser(userId, stream)
        }, 1000);
    })
})

socket.on('user-disconnected', userId => {
    if (peers[userId]) {
        peers[userId].close()
    }
})

myPeer.on('open', id => {
    socket.emit('join-room', ROOM_ID.concat(" ").concat(id))
})

function addVideoStream(video, stream) {
    video.srcObject = stream
    video.addEventListener('loadedmetadata', () => {
        video.play()
    })
    videoGrid.append(video)
}

function connectToNewUser(userId, stream) {
    const call = myPeer.call(userId, stream)
    const video = document.createElement('video')
    call.on('stream', userVideoStream => {
        addVideoStream(video, userVideoStream)
    })
    call.on('close', () => {
        video.remove()
    })
    peers[userId] = call
}

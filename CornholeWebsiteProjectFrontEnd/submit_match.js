const token = localStorage.getItem("jwt");

function parseJwt(t) {
    const base64Url = t.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(atob(base64Url));
}

  if (!token) {
    alert("Please log in first.");
    window.location.href = "index.html";
    throw new Error("No JWT found");
  }
  const { userId: loggedInUserId, username: loggedInUserName } = parseJwt(token);

document.getElementById("match-form").addEventListener("submit", async function (e) {
    e.preventDefault();
    console.log("Submitting match...");
    if (!token) {
        alert("You must be logged in to submit a match.");
        return;
    }

    const body = {
        player1: {
            id: loggedInUserId,
            name: loggedInUserName,
        },
        player2: {
            id: document.getElementById("player2-name").getAttribute("data-user-id") || null,
            name: document.getElementById("player2-name").value
        },
        player1_score: parseInt(document.getElementById("player1-score").value),
        player2_score: parseInt(document.getElementById("player2-score").value),
        location: document.getElementById("match-location").value || null,
        match_date: document.getElementById("match-date").value || null,
        privacy: {
            player1_name_private: document.getElementById("p1-private").checked,
            player2_name_private: document.getElementById("p2-private").checked,
            winner_private: document.getElementById("winner-private").checked,
            score_private: document.getElementById("score-private").checked,
            location_private: document.getElementById("loc-private").checked,
            date_private: document.getElementById("date-private").checked,
        },
        match_stats: []
    };

    // Optional match stats for Player 1 (to be added another time)
    // const p1Throws = document.getElementById("p1-throws").value;
    // if (p1Throws !== "") {
    //     body.match_stats.push({
    //         player_id: body.player1.id,
    //         player_name: body.player1.name,
    //         throws: parseInt(p1Throws) || 0,
    //         aces: parseInt(document.getElementById("p1-aces").value) || 0,
    //         rounds_won: parseInt(document.getElementById("p1-rounds").value) || 0,
    //         accuracy: parseFloat(document.getElementById("p1-accuracy").value) || 0,
    //     });
    // }

    // // Optional match stats for Player 2
    // const p2Throws = document.getElementById("p2-throws").value;
    // if (p2Throws !== "") {
    //     body.match_stats.push({
    //         player_id: body.player2.id,
    //         player_name: body.player2.name,
    //         throws: parseInt(p2Throws) || 0,
    //         aces: parseInt(document.getElementById("p2-aces").value) || 0,
    //         rounds_won: parseInt(document.getElementById("p2-rounds").value) || 0,
    //         accuracy: parseFloat(document.getElementById("p2-accuracy").value) || 0,
    //     });
    // }

    try {
        const res = await fetch(`${baseUrl}/matches`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(body)
        });

        const data = await res.json();
        if (res.ok) {
            alert("Match posted successfully!");
            document.getElementById("match-form").reset();
        } else {
            alert("Error: " + (data.error || "Failed to submit match"));
        }
    } catch (err) {
        console.error("Submission error:", err);
        alert("Failed to submit match.");
    }
});

function goBack()
{
  window.history.back();
}

function attachLiveSearch(inputId, suggestionBoxId) {
    let timeout;
    const inputEl = document.getElementById(inputId);
    const suggestionsEl = document.getElementById(suggestionBoxId);

    inputEl.addEventListener("input", () => {
        clearTimeout(timeout);
        const query = inputEl.value.trim();

        if (!query) {
            suggestionsEl.innerHTML = "";
            return;
        }

        timeout = setTimeout(() => {
            fetch(`${baseUrl}/search-users?q=${encodeURIComponent(query)}`)
                .then((res) => res.json())
                .then((users) => {
                    suggestionsEl.innerHTML = "";

                    if (!Array.isArray(users) || users.length === 0) {
                        suggestionsEl.innerHTML = "<div class='suggestion'>No users found</div>";
                        return;
                    }

                    users.forEach((user) => {
                        const div = document.createElement("div");
                        div.className = "suggestion";
                        div.innerHTML = `
                <img src="${user.profile_image || defaultImg}" alt="pfp" />
                <div class="suggestion-text">
                  <strong>${user.username}</strong>
                  <span>${user.location || ""}</span>
                </div>
              `;

                        div.onclick = () => {
                            inputEl.value = user.username;
                            suggestionsEl.innerHTML = "";
                            inputEl.dataset.userId = user.id; // Store for form submission
                        };

                        suggestionsEl.appendChild(div);
                    });
                })
                .catch((err) => {
                    console.error("Search failed:", err);
                    suggestionsEl.innerHTML = "<div class='suggestion'>Search failed</div>";
                });
        }, 300);
    });

    inputEl.addEventListener("blur", () => {
        setTimeout(() => {
            suggestionsEl.innerHTML = "";
        }, 150); // Delay to allow click event to register
    });
}

// Initialize live search for both players
attachLiveSearch("player1-name", "player1-suggestions");
attachLiveSearch("player2-name", "player2-suggestions");


document.addEventListener("click", function (event) {
    const suggestionBoxes = document.querySelectorAll(".player-suggestion-container");

    suggestionBoxes.forEach((container) => {
        if (!container.contains(event.target)) {
            const box = container.querySelector(".suggestions-box");
            if (box) box.innerHTML = "";
        }
    });
});



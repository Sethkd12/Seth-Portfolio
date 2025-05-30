const baseUrl = "https://backendserver-cool-forest-3620.fly.dev";

async function loadUserName(userId) {
  try {
    const response = await fetch(`${baseUrl}/profile/${userId}`);
    const user = await response.json();
    if (user.error) {
      alert("Error: " + user.error);
      return;
    }
    // Update the heading with the user's name
    document.getElementById("user-name").innerText = `${user.username}'s Match History`;
  } catch (error) {
    console.error("Error fetching user name:", error);
  }
}
function goBack() {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get("id");
  
    if (userId) {
      window.location.href = `profile.html?id=${userId}`;
    } else {
      alert("No user ID found!");
    }
  }
async function loadMatches() {
  // Get userId from URL query string
  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get("id");

  if (!userId) {
    alert("No user ID provided!");
    return;
  }

  // Load user's name to update the heading
  loadUserName(userId);

  try {
    // Fetch matches for the user
    const response = await fetch(`${baseUrl}/matches/${userId}`);
    const matches = await response.json();

    if (matches.error) {
      alert("Error: " + matches.error);
      return;
    }

    // Get the container where matches will be displayed
    const matchesContainer = document.getElementById("matches-container");

    // Check if the user has any matches
    if (matches.length === 0) {
      matchesContainer.innerHTML = "<p>No matches found.</p>";
      return;
    }

    // Loop through the matches and display them
    matches.forEach((match) => {
      // Create an element for each match
      const matchElement = document.createElement("div");
      matchElement.classList.add("match");

      // Set the match content (player names, score, etc.)
      matchElement.innerHTML = `
        <h3>Match ID: ${match.id}</h3>
        <p><strong>Player 1:</strong> ${match.player1} (${match.player1_score})</p>
        <p><strong>Player 2:</strong> ${match.player2} (${match.player2_score})</p>
        <p><strong>Location:</strong> ${match.location}</p>
        <p><strong>Date:</strong> ${new Date(match.match_date).toLocaleDateString()}</p>
        <p><strong>Winner:</strong> ${match.winner}</p>
        <a href="match_details.html?id=${match.id}">View Match Details</a>
      `;

      // Append the match element to the container
      matchesContainer.appendChild(matchElement);
    });
  } catch (error) {
    console.error("Error fetching matches:", error);
    alert("Failed to load matches.");
  }
}

// Load matches when the page is loaded
window.onload = loadMatches;

async function loadProfile() {
  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get("id");

  if (!userId) {
    alert("No user ID provided!");
    return;
  }

  try {
    // 1) Fetch profile data
    const response = await fetch(`${baseUrl}/profile/${userId}`);
    const user = await response.json();

    if (!response.ok || user.error) {
      alert("Error: " + (user.error || response.statusText));
      return;
    }

    // 2) Display user info
    document.getElementById("name").innerText = user.username;
    document.getElementById("bio").innerText  = user.bio  || "No bio available.";
    document.getElementById("loco").innerText = user.location || "No location provided.";

    // 3) Set the Match History link, if it exists
    const matchesLinkEl = document.getElementById("matches-link");
    if (matchesLinkEl) {
      matchesLinkEl.href = `matches.html?id=${userId}`;
    }

    // 4) Pre-fill the edit form, if it’s in the DOM
    if (document.getElementById("profile-form")) {
      document.getElementById("edit-username").value      = user.username;
      document.getElementById("edit-location").value      = user.location || "";
      document.getElementById("edit-bio").value           = user.bio      || "";
      document.getElementById("edit-profile-img").value   = user.profile_image || defaultImg;
    }

    // 5) Update profile image, if present
    const profileImg = document.getElementById("profile-img");
    if (profileImg) {
      profileImg.src = user.profile_image || defaultImg;
    }

    // 6) Fetch and render match history
    const matchRes = await fetch(`${baseUrl}/matches/${userId}`);
    const matches = await matchRes.json();

    const matchHistory = document.getElementById("match-history");
    if (matchHistory) {
      matchHistory.innerHTML = "<h3>Match History</h3>";

      if (!Array.isArray(matches) || matches.length === 0) {
        matchHistory.innerHTML += "<p>No matches found.</p>";
      } else {
        matches.forEach(match => {
          const matchEl = document.createElement("div");
          matchEl.className = "match-item";
          matchEl.innerHTML = `
            <p><strong>${match.player1}</strong> vs <strong>${match.player2}</strong></p>
            <p>Score: ${match.player1_score} - ${match.player2_score}</p>
            <p>Winner: ${match.winner}</p>
            <p>Location: ${match.location}</p>
            <p>Date: ${match.match_date 
                ? new Date(match.match_date).toLocaleString()
                : "Hidden"}</p>
            <hr />
          `;
          matchHistory.appendChild(matchEl);
        });
      }
    }
  } catch (error) {
    console.error("Error loading profile or match history:", error);
    alert("Failed to load profile or match history.");
  }
}

// Toggle between edit mode and view mode
function toggleEditMode() {
  const form = document.getElementById("profile-form");
  form.style.display =
    form.style.display === "none" || form.style.display === ""
      ? "block"
      : "none";
}
function goBack()
{
  window.history.back();
}
// Save updated profile data
async function saveProfile(event) {
  event.preventDefault();

  const newUsername   = document.getElementById("edit-username").value;
  const newLocation   = document.getElementById("edit-location").value;
  const newBio        = document.getElementById("edit-bio").value;
  const newProfileImg = document.getElementById("edit-profile-img").value;
  const newPassword   = document.getElementById("edit-password").value;

  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in to edit your profile.");
    return;
  }

  try {
    const response = await fetch(`${baseUrl}/update-profile`, {
      method: "PUT",
      headers: {
        "Content-Type":  "application/json",
        Authorization:   `Bearer ${token}`
      },
      body: JSON.stringify({
        username:      newUsername,
        location:      newLocation,
        bio:           newBio,
        profile_image: newProfileImg,
        password:      newPassword || null
      }),
    });

    const data = await response.json();
    if (!response.ok || data.error) {
      alert("Error updating profile: " + (data.error || response.statusText));
      return;
    }

    // Reflect changes immediately
    document.getElementById("name").innerText    = data.user.username;
    document.getElementById("bio").innerText     = data.user.bio;
    document.getElementById("loco").innerText    = data.user.location;
    document.getElementById("profile-img").src   = data.user.profile_image || defaultImg;
    document.getElementById("profile-form").style.display = "none";

    alert("Profile updated successfully!");
  } catch (error) {
    console.error("Error updating profile:", error);
    alert("Failed to update profile.");
  }
}

// Ensure loadProfile runs once everything’s parsed
window.onload = loadProfile;

const baseUrl = "https://backendserver-cool-forest-3620.fly.dev";
const defaultImg =
  "https://m.media-amazon.com/images/I/91njXdEsC3L._AC_UF1000,1000_QL80_.jpg";

// Store all leagues for searching
let allLeagues = [];

// League Functions
function createLeague(event) {
  event.preventDefault();

  const name = document.getElementById("league-name").value;
  const description = document.getElementById("league-description").value;
  let image = document.getElementById("league-image").value;

  if (!name) {
    alert("League name is required!");
    return;
  }

  // Use default image if none provided
  if (!image) {
    image = defaultImg;
  }

  // Check if user is logged in
  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in to create a league");
    window.location.href = "/index.html";
    return;
  }

  fetch(`${baseUrl}/create-league`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({ name, image, description }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.league) {
        alert("League created successfully!");
        window.location.href = "/leagues.html";
      } else {
        alert("Failed to create league: " + (data.error || "Unknown error"));
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("An error occurred while creating the league.");
    });
}

function loadLeagues() {
  const leaguesContainer = document.getElementById("leagues-container");
  if (!leaguesContainer) return;
  let url = `${baseUrl}/leagues`;
  console.log("Loading leagues...", url);
  fetch(url)
    .then((response) => response.json())
    .then((leagues) => {
      // Store leagues globally for search functionality
      allLeagues = leagues;

      if (!Array.isArray(leagues) || leagues.length === 0) {
        leaguesContainer.innerHTML = "<p>No leagues found</p>";
        return;
      }

      displayLeagues(leagues);
    })
    .catch((error) => {
      console.error("Error:", error);
      leaguesContainer.innerHTML = "<p>Error loading leagues</p>";
    });
}

// Leave a league
function leaveLeague(leagueId) {
  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in");
    return Promise.reject("Not logged in");
  }

  return fetch(`${baseUrl}/leagues/${leagueId}/leave`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    }
  })
    .then(response => {
      if (!response.ok) {
        return response.json().then(data => Promise.reject(data.error || "Failed to leave league"));
      }
      return response.json();
    });
}

// Edit a league (only for owners)
function editLeague(leagueId, leagueData) {
  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in");
    return Promise.reject("Not logged in");
  }

  return fetch(`${baseUrl}/leagues/${leagueId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify(leagueData)
  })
    .then(response => {
      if (!response.ok) {
        return response.json().then(data => Promise.reject(data.error || "Failed to update league"));
      }
      return response.json();
    });
}

// Generate an invite link for a league
function generateInviteLink(leagueId) {
  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in");
    return Promise.reject("Not logged in");
  }

  return fetch(`${baseUrl}/leagues/${leagueId}/invite`, {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
    .then(response => {
      if (!response.ok) {
        return response.json().then(data => Promise.reject(data.error || "Failed to generate invite link"));
      }
      return response.json();
    });
}

// Handle league functions
function handleLeaveLeague(leagueId) {
  if (confirm("Are you sure you want to leave this league? If you are the owner, the league will be deleted.")) {
    leaveLeague(leagueId)
      .then(response => {
        alert(response.message);
        // Redirect to leagues page
        window.location.href = "/leagues.html";
      })
      .catch(error => {
        alert("Failed to leave league: " + error);
      });
  }
}

function handleEditLeague(leagueId) {
  // First fetch current league data
  fetch(`${baseUrl}/leagues/${leagueId}`)
    .then(response => response.json())
    .then(league => {
      // Show edit form
      document.getElementById("league-details").style.display = "none";
      
      // Create edit form if it doesn't exist yet
      let editForm = document.getElementById("edit-league-form");
      if (!editForm) {
        editForm = document.createElement("form");
        editForm.id = "edit-league-form";
        editForm.className = "league-form";
        document.getElementById("league-details").parentNode.appendChild(editForm);
      }
      
      editForm.innerHTML = `
        <h2>Edit League</h2>
        <button type="button" id="cancel-edit" class="secondary-button">Cancel</button>
        
        <label for="edit-league-name">League Name:</label>
        <input type="text" id="edit-league-name" value="${league.name}" required>
        
        <label for="edit-league-description">Description:</label>
        <textarea id="edit-league-description">${league.description || ""}</textarea>
        
        <label for="edit-league-image">League Image URL:</label>
        <input type="text" id="edit-league-image" value="${league.image || ""}">
        
        <button type="submit" id="save-league">Save Changes</button>
      `;
      
      editForm.style.display = "block";
      
      // Add event listeners
      document.getElementById("cancel-edit").addEventListener("click", function() {
        editForm.style.display = "none";
        document.getElementById("league-details").style.display = "block";
      });
      
      editForm.addEventListener("submit", function(event) {
        event.preventDefault();
        
        const updatedData = {
          name: document.getElementById("edit-league-name").value,
          description: document.getElementById("edit-league-description").value,
          image: document.getElementById("edit-league-image").value
        };
        
        editLeague(leagueId, updatedData)
          .then(response => {
            alert("League updated successfully");
            editForm.style.display = "none";
            // Refresh league details
            viewLeague(leagueId);
          })
          .catch(error => {
            alert("Failed to update league: " + error);
          });
      });
    })
    .catch(error => {
      console.error("Error:", error);
      alert("Failed to load league details for editing");
    });
}

function handleGenerateInvite(leagueId) {
  generateInviteLink(leagueId)
    .then(data => {
      const inviteDialog = document.createElement("div");
      inviteDialog.className = "invite-dialog";
      inviteDialog.innerHTML = `
        <div class="invite-content">
          <h3>Invite Link Generated</h3>
          <p>Share this link with others to invite them to the league:</p>
          <input type="text" readonly value="${data.inviteUrl}" id="invite-url">
          <div class="invite-actions">
            <button id="copy-invite">Copy Link</button>
            <button id="close-invite">Close</button>
          </div>
        </div>
      `;
      
      document.body.appendChild(inviteDialog);
      
      document.getElementById("copy-invite").addEventListener("click", function() {
        const inviteUrl = document.getElementById("invite-url");
        inviteUrl.select();
        document.execCommand("copy");
        alert("Invite link copied to clipboard!");
      });
      
      document.getElementById("close-invite").addEventListener("click", function() {
        document.body.removeChild(inviteDialog);
      });
    })
    .catch(error => {
      alert("Failed to generate invite link: " + error);
    });
}

// Check if current user is a member of a league and fetch membership status
function checkLeagueMembership(leagueId) {
  const token = localStorage.getItem("jwt");
  if (!token) {
    return Promise.resolve({ isMember: false });
  }

  return fetch(`${baseUrl}/leagues/${leagueId}/membership`, {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
    .then(response => response.json())
    .catch(error => {
      console.error("Error checking membership:", error);
      return { isMember: false };
    });
}

// Fetch league members
function fetchLeagueMembers(leagueId) {
  return fetch(`${baseUrl}/leagues/${leagueId}/members`)
    .then(response => response.json())
    .catch(error => {
      console.error("Error fetching members:", error);
      return [];
    });
}

// Join a league
function joinLeague(leagueId) {
  const token = localStorage.getItem("jwt");
  if (!token) {
    alert("You must be logged in to join a league");
    return Promise.reject("Not logged in");
  }

  return fetch(`${baseUrl}/leagues/${leagueId}/join`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    }
  })
    .then(response => {
      if (!response.ok) {
        return response.json().then(data => Promise.reject(data.error || "Failed to join"));
      }
      return response.json();
    });
}

function viewLeague(leagueId) {
  // Fetch league details, membership status, and members in parallel
  Promise.all([
    fetch(`${baseUrl}/leagues/${leagueId}`).then(response => response.json()),
    checkLeagueMembership(leagueId),
    fetchLeagueMembers(leagueId)
  ])
    .then(([league, membership, members]) => {
      // Hide leagues list
      document.getElementById("leagues-container").style.display = "none";
      
      // Show league details
      const leagueDetails = document.getElementById("league-details");
      leagueDetails.style.display = "block";
      
      // Update league details
      document.getElementById("league-name").textContent = league.name;
      document.getElementById("league-img").src = league.image || defaultImg;
      
      // Prepare the membership section
      let membershipHTML = '';
      if (membership.isMember) {
        // Add action buttons based on role
        let actionButtons = `
          <button onclick="handleLeaveLeague(${league.id})">Leave League</button>
          <button onclick="handleGenerateInvite(${league.id})">Generate Invite Link</button>
        `;
        
        // Add edit button for owner
        if (membership.role === 'owner') {
          actionButtons = `
            <button onclick="handleEditLeague(${league.id})">Edit League</button>
            ${actionButtons}
          `;
        }
        
        membershipHTML = `
          <div class="membership-status">
            <p>You are a ${membership.role} of this league</p>
            <div class="member-actions">
              ${actionButtons}
            </div>
          </div>
        `;
      } else {
        membershipHTML = `
          <div class="membership-status">
            <p>You are not a member of this league</p>
            <button id="join-league-btn" onclick="handleJoinLeague(${league.id})">Join League</button>
          </div>
        `;
      }
      
      // League info with description and membership
      const leagueInfo = document.getElementById("league-info");
      leagueInfo.innerHTML = `
        <div class="league-description">
          <h3>Description</h3>
          <p>${league.description || "No description available."}</p>
        </div>
        ${membershipHTML}
        <div class="league-members">
          <h3>Members</h3>
          <div id="members-list"></div>
        </div>
      `;
      
      // Add members to list
      const membersList = document.getElementById("members-list");
      if (members.length === 0) {
        membersList.innerHTML = "<p>No members found</p>";
      } else {
        membersList.innerHTML = "";
        members.forEach(member => {
          const memberDiv = document.createElement("div");
          memberDiv.className = "member-item";
          memberDiv.innerHTML = `
            <img src="${member.profile_image || defaultImg}" alt="Profile Image">
            <div class="member-info">
              <p class="member-name">${member.username}</p>
              <p class="member-role">${member.role}</p>
            </div>
          `;
          membersList.appendChild(memberDiv);
        });
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Failed to load league details");
    });
}

function showLeaguesList() {
  // Hide league details
  document.getElementById("league-details").style.display = "none";

  // Show leagues list
  document.getElementById("leagues-container").style.display = "block";
}

// Display leagues in the container
function displayLeagues(leagues) {
  const leaguesContainer = document.getElementById("leagues-container");
  if (!leaguesContainer) return;

  leaguesContainer.innerHTML = "";

  if (leagues.length === 0) {
    leaguesContainer.innerHTML = "<p style='color: white; text-align: center; width: 100%;'>No leagues found</p>";
    return;
  }

  leagues.forEach((league) => {
    const leagueCard = document.createElement("div");
    leagueCard.className = "league-card";
    leagueCard.onclick = () => viewLeague(league.id);

    leagueCard.innerHTML = `
      <img src="${league.image || defaultImg}" alt="${league.name}">
      <h3>${league.name}</h3>
    `;

    leaguesContainer.appendChild(leagueCard);
  });
}

// Search leagues
function searchLeagues(query) {
  if (!query) {
    displayLeagues(allLeagues);
    return;
  }

  query = query.toLowerCase();
  const filteredLeagues = allLeagues.filter(league =>
    league.name.toLowerCase().includes(query) ||
    (league.description && league.description.toLowerCase().includes(query))
  );

  displayLeagues(filteredLeagues);
}

// Check if we're on the leagues page and set up event listeners
document.addEventListener("DOMContentLoaded", function () {
  if (window.location.pathname.includes("leagues")) {
    // Check if there's an invite in the URL
    const urlParams = new URLSearchParams(window.location.search);
    const inviteLeagueId = urlParams.get('invite');
    
    if (inviteLeagueId) {
      // If user is logged in, ask if they want to join the league
      const token = localStorage.getItem("jwt");
      if (token) {
        if (confirm("You've been invited to join a league. Would you like to join?")) {
          handleJoinLeague(inviteLeagueId);
        }
        
        // Remove the invite parameter from URL to avoid repeated prompts
        const newUrl = window.location.pathname;
        window.history.replaceState({}, document.title, newUrl);
      } else {
        // Redirect to login page with the invite information
        window.location.href = `/index.html?redirect=leagues&invite=${inviteLeagueId}`;
        return;
      }
    }
    
    loadLeagues();

    // Set up league search functionality
    const leagueSearchInput = document.getElementById("league-search-input");
    if (leagueSearchInput) {
      leagueSearchInput.addEventListener("input", function () {
        searchLeagues(this.value.trim());
      });
    }
  }
});

// Handle joining a league
function handleJoinLeague(leagueId) {
  joinLeague(leagueId)
    .then(response => {
      alert("Successfully joined the league!");
      // Refresh the league view to show updated membership status
      viewLeague(leagueId);
    })
    .catch(error => {
      alert("Failed to join league: " + error);
    });
}

// Navigation helper function
function goBack() {
  // Check if we're in a specific page to handle special cases
  if (window.location.pathname.includes("leagues")) {
    // If league details are shown, go back to leagues list
    if (document.getElementById("league-details").style.display !== "none") {
      showLeaguesList();
      return;
    }
  }

  // Otherwise go back in browser history
  window.history.back();
}

function login(event) {
  event.preventDefault(); // Prevent form submission refresh

  var username = document.getElementById("u").value;
  var password = document.getElementById("p").value;

  if (!username || !password) {
    alert("Username and password are required!");
    return;
  }

  // Check if we have an invite to handle after login
  const urlParams = new URLSearchParams(window.location.search);
  const inviteLeagueId = urlParams.get('invite');
  const redirect = urlParams.get('redirect');

  fetch(`${baseUrl}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.token) {
        localStorage.setItem("jwt", data.token); // Store token for authentication
        console.log("Token:", data.token);
        let userId = data.userId;
        alert("Login successful!");
        
        // If we have an invite, redirect to the leagues page with the invite
        if (inviteLeagueId && redirect === 'leagues') {
          window.location.href = `/leagues.html?invite=${inviteLeagueId}`;
        } else {
          window.location.href = `./profile.html?id=${userId}`; // Default redirect to profile
        }
      } else {
        alert("Login failed: " + (data.error || "Unknown error"));
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("An error occurred during login.");
    });
}

function register(event) {
  event.preventDefault(); // Prevent form from refreshing the page

  var username = document.getElementById("u").value;
  var password = document.getElementById("p").value;
  var bio = document.getElementById("b").value;
  var location = document.getElementById("l").value;

  if (!username || !password) {
    alert("Username and password are required!");
    return;
  }

  // Check if we have an invite to handle after registration
  const urlParams = new URLSearchParams(window.location.search);
  const inviteLeagueId = urlParams.get('invite');
  const redirect = urlParams.get('redirect');

  fetch(`${baseUrl}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username,
      password,
      bio: bio || "New player", // Default bio (optional)
      profile_image: "", // Empty profile image by default
      location: location || "", // Empty location by default
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.token) {
        localStorage.setItem("jwt", data.token); // Store token for authentication
        alert("Registration successful!");
        console.log("User:", data);
        
        // If we have an invite, redirect to the leagues page with the invite
        if (inviteLeagueId && redirect === 'leagues') {
          window.location.href = `/leagues.html?invite=${inviteLeagueId}`;
        } else {
          window.location.href = './profile.html?id=' + data.userId + ''; // Default redirect to profile
        }
      } else {
        alert("Registration failed: " + (data.error || "Unknown error"));
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("An error occurred during registration.");
    });
}

// Live user search with suggestions and profile pictures
let searchTimeout;

document.getElementById("search-input").addEventListener("input", function () {
  clearTimeout(searchTimeout);
  console.log("Searching for:", this.value);

  const query = this.value.trim();
  if (!query) {
    document.getElementById("search-suggestions").innerHTML = "";
    return;
  }

  // Debounce to avoid spamming requests
  searchTimeout = setTimeout(() => {
    fetch(`${baseUrl}/search-users?q=${encodeURIComponent(query)}`)
      .then(res => res.json())
      .then(users => {
        const container = document.getElementById("search-suggestions");
        container.innerHTML = "";

        if (!Array.isArray(users) || users.length === 0) {
          container.innerHTML = "<div class='suggestion'>No users found</div>";
          return;
        }

        users.forEach(user => {
          const suggestion = document.createElement("div");
          suggestion.className = "suggestion";
          suggestion.onclick = () => {
            window.location.href = `./profile.html?id=${user.id}`;
          };

          suggestion.innerHTML = `
            <img src="${user.profile_image || defaultImg}" alt="pfp" />
            <div class="suggestion-text">
              <strong>${user.username}</strong>
              <span>${user.location || ""}</span>
            </div>
          `;

          container.appendChild(suggestion);
        });
      })
      .catch(err => {
        console.error("Search error:", err);
        document.getElementById("search-suggestions").innerHTML = "<div class='suggestion'>Search failed</div>";
      });
  }, 250);
});

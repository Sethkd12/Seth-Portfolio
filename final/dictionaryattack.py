## Final Project
import hashlib
import time
import math
import matplotlib.pyplot as plt
import matplotlib.lines as mlines
## retrive file that contains the list of words and
## convert it into a list
file = "10-million-password-list-top-500.txt"
wordList = open(file).readlines()
wordList = [line.strip() for line in wordList]
## making lists for data for each algorithm
strengths256 = []
passwords256 = []
y_axis256 = []
no_guesses_256 = []

strengths512 = []
passwords512 = []
y_axis512 = []
no_guesses_512 = []
## ask user for password input
password = ""
while True:
    password = input("Enter Password:")
    ## amount of unique characters in password (for difficulty calculation)
    strength = math.log2(len(set(password))) * len(password)
    if password == "q":
        break
    ## create a list of the two hash algorithms we will use
    ## and use a for loop so that we can compare the efficency
    ## to crack the password for both
    algs = {"sha256", "sha512"}
    for alg in algs:
        ## creates a hash for the password the user inputted 
        hasher = hashlib.new(alg)
        hasher.update(password.encode())
        password_hash = hasher.hexdigest()
        ## boolean for whether of not the password gets found
        found = False
        ## number of guesses
        guesses = 0
        ## the time that the dictionary attack begins
        start = time.time()
        ## tries a dictionary attack for a single word password
        for word in wordList:
            if(len(word) != len(password)):
                continue
            guesses += 1
            word_hasher = hashlib.new(alg)
            word_hasher.update(word.encode())
            word_hash = word_hasher.hexdigest()
            if word_hash == password_hash:
                print(f"Cracked {alg}: {word}")
                found = True
                break
        ## tries a dictionary attack for a double word password
        ## if the password is not found trying single word passwords
        if not found:
            for word in wordList:
                for word2 in wordList:
                    potentialPass = word + word2
                    if(len(potentialPass) != len(password)):
                        continue
                    guesses += 1
                    word_hasher = hashlib.new(alg)
                    word_hasher.update(potentialPass.encode())
                    word_hash = word_hasher.hexdigest()
                    if word_hash == password_hash:
                        print(f"Cracked {alg}: {potentialPass}")
                        found = True
                        break
        ## tries a dictionary attack for a double word password
        ## if the password is not found trying single word 
        # or double word passwords
        if not found:
            for word in wordList:
                for word2 in wordList:
                    for word3 in wordList:
                        potentialPass = word + word2 + word3
                        if(len(potentialPass) != len(password)):
                            continue
                        guesses += 1
                        word_hasher = hashlib.new(alg)
                        word_hasher.update(potentialPass.encode())
                        word_hash = word_hasher.hexdigest()
                        if word_hash == password_hash:
                            print(f"Cracked {alg}: {potentialPass}")
                            found = True
                            break
                    if found:
                        break
                if found:
                    break
        ## gets the time of code getting cracked and displays the time
        ## it took to crack it
        end = time.time()
        totalTime = end - start
        if alg == "sha256":
            strengths256.append(strength)
            passwords256.append(password)
            y_axis256.append(totalTime)
            no_guesses_256.append(guesses)
        if alg == "sha512":
            strengths512.append(strength)
            passwords512.append(password)
            y_axis512.append(totalTime)
            no_guesses_512.append(guesses)
            
        if found:
            print(f"Time to crack: {(end - start):.20f}")
            print("\n")
colors = [
    'blue', 'green', 'red', 'purple', 'orange', 'yellow', 'pink', 'brown', 'gray', 'cyan',
    'olive', 'black', 'magenta', 'lime', 'teal', 'navy', 'turquoise', 'indigo', 'violet', 
    'gold', 'beige', 'azure', 'maroon', 'salmon', 'chocolate', 'orchid', 'plum', 'peru', 
    'crimson', 'khaki', 'coral']


# Plot for SHA256
plt.figure(figsize=(10, 5))
for i in range(len(no_guesses_256)):
    label = f"{passwords256[i]} (No. Guesses: {no_guesses_256[i]})"
    plt.scatter(strengths256[i], y_axis256[i], color=colors[i], label=label)
plt.title(f"Password Strength vs. Time Taken to Crack SHA256\nSize of Dictionary:{len(wordList)}")
plt.xlabel('Password Strength')
plt.ylabel('Time to Crack SHA512 (seconds)')
plt.legend(loc='upper left', bbox_to_anchor=(1, 1))
plt.tight_layout()
plt.savefig("SHA256_plot.png")
# Plot for SHA512
plt.figure(figsize=(10, 5))
for i in range(len(no_guesses_512)):
    label = f"{passwords512[i]} (No. Guesses: {no_guesses_512[i]})"
    plt.scatter(strengths512[i], y_axis512[i], color=colors[i], label=label)
plt.title(f"Password Strength vs. Time Taken to Crack SHA512\nSize of Dictionary:{len(wordList)}")
plt.xlabel("Password Strength")
plt.ylabel('Time to Crack SHA512 (seconds)')
plt.legend(loc='upper left', bbox_to_anchor=(1, 1))
plt.tight_layout()
plt.savefig("SHA512_plot.png")
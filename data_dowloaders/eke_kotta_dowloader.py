from bs4 import BeautifulSoup
import requests

enekIdx = 1

def getSong(url):
   
   # Weboldal betöltése
   r = requests.get(url)
   s = BeautifulSoup(r.content)

   enek = s.find("p", class_="Kotta")

   img_tag = enek.select("img")  # Az első képet keresi

   if img_tag:
        img_url = img_tag[0]["src"]
        
        #Ha az URL relatív, akkor kiegészítjük az alap URL-lel
        
        from urllib.parse import urljoin
        img_url = urljoin(url, img_url)
        
        #Letöltjük a képet
        img_data = requests.get(img_url).content

        # Kép mentése fájlként
        with open(rf"/mnt/E/ProgramFiles/EKE/EKE_Enekeskonyv_App/Data/kottak/kotta_{enekIdx}.jpg", "wb") as f:
            f.write(img_data)


while enekIdx <= 463:
    url = f"https://ekealapitvany.hu/enekeskonyv/{str(enekIdx).zfill(3)}.html"

    # img_data = requests.get(url).content
    img_data = getSong(url)

    # Kép mentése fájlként
    # with open(rf"/mnt/E/ProgramFiles/EKE/EKE_Enekeskonyv_App/Data/kottak\kotta_{enekIdx}.jpg", "wb") as f:
    #     f.write(img_data)

    #getSong(url)
    enekIdx += 1
    print(f"Letöltve: {enekIdx} ✅")
    


# class = .
# id = #
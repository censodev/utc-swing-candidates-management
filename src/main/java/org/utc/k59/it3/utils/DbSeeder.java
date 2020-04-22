package org.utc.k59.it3.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.utc.k59.it3.dto.CandidateJsonDTO;
import org.utc.k59.it3.models.Candidate;
import org.utc.k59.it3.models.Province;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbSeeder {
    public static void seedAll() throws FileNotFoundException {
        if (ServicesManager.provinceRepository.find(1) != null)
            return;
        seedProvinces();
        seedCandidate();
    }

    public static void seedCandidate() throws FileNotFoundException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Type typeToken = new TypeToken<List<CandidateJsonDTO>>(){}.getType();
        List<CandidateJsonDTO> candidatesJson = gson
                .fromJson(new FileReader("src/main/resources/mock_candidates_100.json"), typeToken);
        List<Candidate> candidates = candidatesJson.stream()
                .map(json -> {
                    Candidate candidate = new Candidate();
                    candidate.setName(json.getName());
                    candidate.setGender(json.getGender());
                    candidate.setProvinceId(json.getProvinceId());
                    candidate.setBirthDate(Instant.parse(json.getBirthDate()).atZone(ZoneId.systemDefault()).toLocalDate());
                    candidate.setMathMark(json.getMathMark());
                    candidate.setPhysicsMark(json.getPhysicsMark());
                    candidate.setChemistryMark(json.getChemistryMark());
                    return candidate;
                })
                .collect(Collectors.toList());
        ServicesManager.candidateRepository.save(candidates);
    }

    public static void seedProvinces() {
        String[] provinces = {
                "An Giang",
                "Bà Rịa - Vũng Tàu",
                "Bắc Giang",
                "Bắc Kạn",
                "Bạc Liêu",
                "Bắc Ninh",
                "Bến Tre",
                "Bình Định",
                "Bình Dương",
                "Bình Phước",
                "Bình Thuận",
                "Cà Mau",
                "Cao Bằng",
                "Đắk Lắk",
                "Đắk Nông",
                "Điện Biên",
                "Đồng Nai",
                "Đồng Tháp",
                "Gia Lai",
                "Hà Giang",
                "Hà Nam",
                "Hà Tĩnh",
                "Hải Dương",
                "Hậu Giang",
                "Hòa Bình",
                "Hưng Yên",
                "Khánh Hòa",
                "Kiên Giang",
                "Kon Tum",
                "Lai Châu",
                "Lâm Đồng",
                "Lạng Sơn",
                "Lào Cai",
                "Long An",
                "Nam Định",
                "Nghệ An",
                "Ninh Bình",
                "Ninh Thuận",
                "Phú Thọ",
                "Quảng Bình",
                "Quảng Nam",
                "Quảng Ngãi",
                "Quảng Ninh",
                "Quảng Trị",
                "Sóc Trăng",
                "Sơn La",
                "Tây Ninh",
                "Thái Bình",
                "Thái Nguyên",
                "Thanh Hóa",
                "Thừa Thiên Huế",
                "Tiền Giang",
                "Trà Vinh",
                "Tuyên Quang",
                "Vĩnh Long",
                "Vĩnh Phúc",
                "Yên Bái",
                "Phú Yên",
                "Cần Thơ",
                "Đà Nẵng",
                "Hải Phòng",
                "Hà Nội",
                "TP HCM"
        };

        List<Province> provinceList = new ArrayList<>();

        for (String provinceName: provinces) {
            Province province = new Province();
            province.setName(provinceName);
            provinceList.add(province);
        }

        ServicesManager.provinceRepository.save(provinceList);
    }
}

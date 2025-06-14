package com.bkopec.annonces.util;

import com.bkopec.annonces.entity.Departement;
import com.bkopec.annonces.repository.DepartementRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final ObjectMapper objectMapper;

    private static final String DEPARTEMENTS_JSON = """
    [
        { "code": "01", "dep_name": "Ain", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "02", "dep_name": "Aisne", "reg_name": "Hauts-de-France" },
        { "code": "03", "dep_name": "Allier", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "04", "dep_name": "Alpes-de-Haute-Provence", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "05", "dep_name": "Hautes-Alpes", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "06", "dep_name": "Alpes-Maritimes", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "07", "dep_name": "Ardèche", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "08", "dep_name": "Ardennes", "reg_name": "Grand Est" },
        { "code": "09", "dep_name": "Ariège", "reg_name": "Occitanie" },
        { "code": "10", "dep_name": "Aube", "reg_name": "Grand Est" },
        { "code": "11", "dep_name": "Aude", "reg_name": "Occitanie" },
        { "code": "12", "dep_name": "Aveyron", "reg_name": "Occitanie" },
        { "code": "13", "dep_name": "Bouches-du-Rhône", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "14", "dep_name": "Calvados", "reg_name": "Normandie" },
        { "code": "15", "dep_name": "Cantal", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "16", "dep_name": "Charente", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "17", "dep_name": "Charente-Maritime", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "18", "dep_name": "Cher", "reg_name": "Centre-Val de Loire" },
        { "code": "19", "dep_name": "Corrèze", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "21", "dep_name": "Côte-d'Or", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "22", "dep_name": "Côtes-d'Armor", "reg_name": "Bretagne" },
        { "code": "23", "dep_name": "Creuse", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "24", "dep_name": "Dordogne", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "25", "dep_name": "Doubs", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "26", "dep_name": "Drôme", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "27", "dep_name": "Eure", "reg_name": "Normandie" },
        { "code": "28", "dep_name": "Eure-et-Loir", "reg_name": "Centre-Val de Loire" },
        { "code": "29", "dep_name": "Finistère", "reg_name": "Bretagne" },
        { "code": "2A", "dep_name": "Corse-du-Sud", "reg_name": "Corse" },
        { "code": "2B", "dep_name": "Haute-Corse", "reg_name": "Corse" },
        { "code": "30", "dep_name": "Gard", "reg_name": "Occitanie" },
        { "code": "31", "dep_name": "Haute-Garonne", "reg_name": "Occitanie" },
        { "code": "32", "dep_name": "Gers", "reg_name": "Occitanie" },
        { "code": "33", "dep_name": "Gironde", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "34", "dep_name": "Hérault", "reg_name": "Occitanie" },
        { "code": "35", "dep_name": "Ille-et-Vilaine", "reg_name": "Bretagne" },
        { "code": "36", "dep_name": "Indre", "reg_name": "Centre-Val de Loire" },
        { "code": "37", "dep_name": "Indre-et-Loire", "reg_name": "Centre-Val de Loire" },
        { "code": "38", "dep_name": "Isère", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "39", "dep_name": "Jura", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "40", "dep_name": "Landes", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "41", "dep_name": "Loir-et-Cher", "reg_name": "Centre-Val de Loire" },
        { "code": "42", "dep_name": "Loire", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "43", "dep_name": "Haute-Loire", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "44", "dep_name": "Loire-Atlantique", "reg_name": "Pays de la Loire" },
        { "code": "45", "dep_name": "Loiret", "reg_name": "Centre-Val de Loire" },
        { "code": "46", "dep_name": "Lot", "reg_name": "Occitanie" },
        { "code": "47", "dep_name": "Lot-et-Garonne", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "48", "dep_name": "Lozère", "reg_name": "Occitanie" },
        { "code": "49", "dep_name": "Maine-et-Loire", "reg_name": "Pays de la Loire" },
        { "code": "50", "dep_name": "Manche", "reg_name": "Normandie" },
        { "code": "51", "dep_name": "Marne", "reg_name": "Grand Est" },
        { "code": "52", "dep_name": "Haute-Marne", "reg_name": "Grand Est" },
        { "code": "53", "dep_name": "Mayenne", "reg_name": "Pays de la Loire" },
        { "code": "54", "dep_name": "Meurthe-et-Moselle", "reg_name": "Grand Est" },
        { "code": "55", "dep_name": "Meuse", "reg_name": "Grand Est" },
        { "code": "56", "dep_name": "Morbihan", "reg_name": "Bretagne" },
        { "code": "57", "dep_name": "Moselle", "reg_name": "Grand Est" },
        { "code": "58", "dep_name": "Nièvre", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "59", "dep_name": "Nord", "reg_name": "Hauts-de-France" },
        { "code": "60", "dep_name": "Oise", "reg_name": "Hauts-de-France" },
        { "code": "61", "dep_name": "Orne", "reg_name": "Normandie" },
        { "code": "62", "dep_name": "Pas-de-Calais", "reg_name": "Hauts-de-France" },
        { "code": "63", "dep_name": "Puy-de-Dôme", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "64", "dep_name": "Pyrénées-Atlantiques", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "65", "dep_name": "Hautes-Pyrénées", "reg_name": "Occitanie" },
        { "code": "66", "dep_name": "Pyrénées-Orientales", "reg_name": "Occitanie" },
        { "code": "67", "dep_name": "Bas-Rhin", "reg_name": "Grand Est" },
        { "code": "68", "dep_name": "Haut-Rhin", "reg_name": "Grand Est" },
        { "code": "69", "dep_name": "Rhône", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "70", "dep_name": "Haute-Saône", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "71", "dep_name": "Saône-et-Loire", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "72", "dep_name": "Sarthe", "reg_name": "Pays de la Loire" },
        { "code": "73", "dep_name": "Savoie", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "74", "dep_name": "Haute-Savoie", "reg_name": "Auvergne-Rhône-Alpes" },
        { "code": "75", "dep_name": "Paris", "reg_name": "Île-de-France" },
        { "code": "76", "dep_name": "Seine-Maritime", "reg_name": "Normandie" },
        { "code": "77", "dep_name": "Seine-et-Marne", "reg_name": "Île-de-France" },
        { "code": "78", "dep_name": "Yvelines", "reg_name": "Île-de-France" },
        { "code": "79", "dep_name": "Deux-Sèvres", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "80", "dep_name": "Somme", "reg_name": "Hauts-de-France" },
        { "code": "81", "dep_name": "Tarn", "reg_name": "Occitanie" },
        { "code": "82", "dep_name": "Tarn-et-Garonne", "reg_name": "Occitanie" },
        { "code": "83", "dep_name": "Var", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "84", "dep_name": "Vaucluse", "reg_name": "Provence-Alpes-Côte d'Azur" },
        { "code": "85", "dep_name": "Vendée", "reg_name": "Pays de la Loire" },
        { "code": "86", "dep_name": "Vienne", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "87", "dep_name": "Haute-Vienne", "reg_name": "Nouvelle-Aquitaine" },
        { "code": "88", "dep_name": "Vosges", "reg_name": "Grand Est" },
        { "code": "89", "dep_name": "Yonne", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "90", "dep_name": "Territoire de Belfort", "reg_name": "Bourgogne-Franche-Comté" },
        { "code": "91", "dep_name": "Essonne", "reg_name": "Île-de-France" },
        { "code": "92", "dep_name": "Hauts-de-Seine", "reg_name": "Île-de-France" },
        { "code": "93", "dep_name": "Seine-Saint-Denis", "reg_name": "Île-de-France" },
        { "code": "94", "dep_name": "Val-de-Marne", "reg_name": "Île-de-France" },
        { "code": "95", "dep_name": "Val-d'Oise", "reg_name": "Île-de-France" },
        { "code": "971", "dep_name": "Guadeloupe", "reg_name": "Guadeloupe" },
        { "code": "972", "dep_name": "Martinique", "reg_name": "Martinique" },
        { "code": "973", "dep_name": "Guyane", "reg_name": "Guyane" },
        { "code": "974", "dep_name": "La Réunion", "reg_name": "La Réunion" },
        { "code": "976", "dep_name": "Mayotte", "reg_name": "Mayotte" }
    ]
    """;

    @Autowired
    public DataLoader(DepartementRepository departementRepository, ObjectMapper objectMapper) {
        this.departementRepository = departementRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (departementRepository.count() == 0) {
            try {
                List<Departement> departements = objectMapper.readValue(
                        DEPARTEMENTS_JSON,
                        new TypeReference<List<Departement>>() {}
                );
                departementRepository.saveAll(departements);
                System.out.println("--- 101 French Departments Initialized! ---");
            } catch (IOException e) {
                System.err.println("Error loading department data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("--- Departments already exist in DB. Skipping initialization. ---");
        }
    }
}
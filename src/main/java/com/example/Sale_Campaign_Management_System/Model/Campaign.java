package com.example.Sale_Campaign_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales_campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String title;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CampaignProduct> campaignProductList = new ArrayList<>();

    public Campaign(LocalDate startDate, LocalDate endDate, String title, List<CampaignProduct> campaignProductList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.campaignProductList = campaignProductList;
    }
}

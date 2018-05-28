package com.crosoften.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class ImageProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isModerator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIgnore
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    @JsonIgnore
    private Images image;

    public ImageProfile(boolean isModerator, Profile profile, Images image) {
        this.isModerator = isModerator;
        this.profile = profile;
        this.image = image;
    }

    public ImageProfile() {
    }

}

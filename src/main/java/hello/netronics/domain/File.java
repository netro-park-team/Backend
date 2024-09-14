package hello.netronics.domain;

import jakarta.persistence.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    // getters and setters
}
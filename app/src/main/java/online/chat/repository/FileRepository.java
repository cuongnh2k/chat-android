package online.chat.repository;

import javax.inject.Inject;

import online.chat.network.FileApiService;

/**
 * @author hieutt (tora262)
 */
public class FileRepository {
    private final FileApiService fileApiService;

    @Inject
    public FileRepository(FileApiService fileApiService) {
        this.fileApiService = fileApiService;
    }
}
